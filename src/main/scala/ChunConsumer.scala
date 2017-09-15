import _root_.kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.sql._
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.types._
import org.apache.spark.sql.types.{StringType, StructType}
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream._


/**
  * Created by ctam on 6/22/17.
  */
object ChunConsumer {
  def main(args: Array[String])= {

    if (args.length < 2){
      println("please enter host:port, topic, interval")
      exit(1)
    }else{
      println(s"Talking to Kafka host ${args(0)}, to topic ${args(1)} every ${args(2)} second")
    }

    //hiveContext.setConf("hive.metastore.uris","thrift://sparke3.non-sec.support.com:9083")
    //hiveContext.setConf("hive.metastore.warehouse.dir","file:///tmp/sparktest")
    val checkpointDir = "/tmp/checkpoint"
    var directKafkaStream : InputDStream[(String, String)]= null
    var newStream : DStream[Row]= null
    def createStreamingContext():StreamingContext = {
      println("Creating new StreamingContext")


      val conf = new SparkConf().setAppName("KafkaToHive").setMaster("local[2]")
      val sc = new SparkContext(conf)
      val ssc = new StreamingContext(sc, Seconds(args(2).toInt))
      //val x = KafkaUtils.createDirectStream(ssc, LocationStrategies.PreferConsistent, ConsumerStrategies.Subscribe[String, String](topics, kafkaPs));

      val kafkaParams = Map[String, String](
        KafkaUtils.securityProtocolConfig -> "PLAINTEXT",
        "bootstrap.servers" -> args(0)
      )
      ssc.checkpoint(checkpointDir)
       directKafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, Set(args(1)))
       newStream = directKafkaStream.map(y => Row(y._1,y._2))
      ssc
    }

    val ssc = StreamingContext.getOrCreate(checkpointDir, createStreamingContext)
    val hiveContext = new HiveContext(ssc.sparkContext)

    println("Creating Kafka connection")
    def my_func(x : RDD[Row]) : Unit = {
      val schema =
        StructType(
          StructField("key", StringType, true) ::
            StructField("value", StringType, true) :: Nil)
      //println(x.getClass)
      if(!x.isEmpty()){
        println("number of partition " + x.getNumPartitions)
        println("I am not null")
        x.foreach(y => println(s"The key is ${y.getString(0)} and value is ${y.getString(1)}"))
        val df = hiveContext.createDataFrame(x,schema)
        df.show()
        df.write.mode("append").format("Parquet").saveAsTable("test")
        hiveContext.sql("select count(*) from test").show
      }else{
        println("I am null")
      }
    }
    newStream.window(Seconds(args(2).toInt),Seconds(args(2).toInt)).foreachRDD(my_func(_))
    ssc.start()
    ssc.awaitTermination()
  }

}

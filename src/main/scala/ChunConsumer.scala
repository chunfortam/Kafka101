import _root_.kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._

/**
  * Created by ctam on 6/22/17.
  */
class ChunConsumer {

    val checkpointDirectory = "/tmp/chuncheckpoint"
    val kafkaParams = Map[String, String](
      KafkaUtils.securityProtocolConfig -> "PLAINTEXT",
      //"metadata.broker.list" -> "spark.sec.support.com:6667",
      "bootstrap.servers" -> "sparke3:6667"
      //"key.deserializer" ->"org.apache.kafka.common.serialization.StringDeserializer",
      //"value.deserializer" ->"org.apache.kafka.common.serialization.StringDeserializer",
      // "group.id"-> "mytopicgroup"
      //"zookeeper.connection.timeout.ms" -> "10000",
      //"zookeeper.connect" -> "sparkb1.sec.support.com:2181,sparkb2.sec.support.com:2181,sparkb4.sec.support.com:2181"
    )
    val ssc = StreamingContext.getOrCreate(checkpointDirectory, createStreamingContext _)
  //props.put("client.id", "ScalaProducerExample")
    //props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    //props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    val directKafkaStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, Set("ETL"))
    //directKafkaStream.saveAsTextFiles("/tmp/streaming_output")
    //ssc.start()
    //ssc.awaitTermination()
    directKafkaStream.foreachRDD(rdd => rdd.collect.foreach(println))

    def createStreamingContext(): StreamingContext = {

      val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
      val ssc = new StreamingContext(conf, Seconds(1))
      ssc.checkpoint(checkpointDirectory)
      ssc
    }

    def startConsuming(): Unit ={
      ssc.start()
      ssc.awaitTermination()
    }


}

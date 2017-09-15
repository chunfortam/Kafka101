import _root_.kafka.serializer.StringDecoder
import org.apache.hadoop.mapred.TextOutputFormat
import org.apache.spark.SparkConf
import org.apache.spark.streaming._

/**
  * Created by ctam on 7/6/17.
  */
object DummyConsumer {

  def main(args: Array[String])= {
    if(args.length != 1){
      println("stupid, put the nc -lk host in argument")
      exit
    }
    val conf = new SparkConf().setAppName("WordPrint")
    //conf.set("spark.executor.extraJavaOptions", "-Djava.security.auth.login.config=/Users/ctam/IdeaProjects/Kafka101/src/main/resources/my.jaas")
    //conf.set("spark.driver.extraJavaOptions", "-Djava.security.auth.login.config=/Users/ctam/IdeaProjects/Kafka101/src/main/resources/my.jaas")
    //conf.set("spark.files","/Users/ctam/IdeaProjects/Kafka101/src/main/resources/my.jaas,/Users/ctam/IdeaProjects/Kafka101/src/main/resources/chun.keytab")

    val ssc = new StreamingContext(conf, Seconds(1))
    ssc.checkpoint("/tmp/checkpoint")
    println("arg is " + args(0))
    val lines = ssc.socketTextStream(args(0), 9999)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
    wordCounts.print()
    wordCounts.saveAsTextFiles("/tmp/output/","txt")
    ssc.start()             // Start the computation
    ssc.awaitTermination()  // Wait for the computation to terminate


  }

}

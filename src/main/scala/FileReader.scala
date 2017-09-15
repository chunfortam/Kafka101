/**
  * Created by Bozis on 6/19/2017.
  */

import kafka.serializer.StringDecoder
import org.apache.kafka.clients.producer.{ProducerRecord, KafkaProducer}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.io.Source
import scala.util.Random
import java.util.{Date, Properties}

object ChunFileReader {
  def main(args: Array[String])={

    if(args.length < 3){
      println("please enter host:port, topic, number of record being insert")
      exit(1)
    }else{
      println(s"Sending message to Kafka host ${args(0)}, to topic ${args(1)}, ${args(2)} times")
    }
    val logFile = "omniture-logs.tsv"
    //val products = "D:\\IdeaProjects\\text\\src\\main\\resources\\products.tsv"
   // val users = "D:\\IdeaProjects\\text\\src\\main\\resources\\users.tsv"

    val logFileSource = Source.fromInputStream(getClass.getResourceAsStream(logFile)).getLines().toList
    //val logFileSource = Source.fromFile(logFile).getLines().toList
    val fileSize = logFileSource.size
    var newLine = Array[String]()

    val props = new Properties()
    props.put("bootstrap.servers", args(0))
    //props.put("client.id", "ScalaProducerExample")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)


    for(i <- 1 to args(2).toInt){
      val random = new Random()
      val lineList = logFileSource(random.nextInt(fileSize)).split("\t")
      val replacementLineList = logFileSource(random.nextInt(fileSize)).split("\t")
      var columnNumber = random.nextInt(lineList.size)
      var finish = false
      //keasdfa
      while (finish != true ){
        if (replacementLineList(columnNumber) != lineList(columnNumber))
        {
          // println("the column value is :$" + replacementLineList(columnNumber) + ", The id is :$" + lineList(0) + ", The replaced value is :$" + lineList(columnNumber))
          newLine = lineList.updated(columnNumber,replacementLineList(columnNumber))
          finish = true
        }
        columnNumber = random.nextInt(lineList.size)
      }

      println("The following is being ingested to Kafka")
      val message = newLine.mkString("\t")
      println(message)
      val data = new ProducerRecord[String,String](args(1),i.toString,message)
      producer.send(data)
    }
    producer.close()

  }

}

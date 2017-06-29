/**
  * Created by Bozis on 6/19/2017.
  */

import org.apache.kafka.clients.producer.{ProducerRecord, KafkaProducer}
import scala.io.Source
import scala.util.Random
import java.util.Properties

class FileReader {

    val logFile = "omniture-logs.tsv"
    //val products = "D:\\IdeaProjects\\text\\src\\main\\resources\\products.tsv"
   // val users = "D:\\IdeaProjects\\text\\src\\main\\resources\\users.tsv"

    val logFileSource = Source.fromInputStream(getClass.getResourceAsStream(logFile)).getLines().toList
    //val logFileSource = Source.fromFile(logFile).getLines().toList
    val fileSize = logFileSource.size
    var newLine = Array[String]()
    val props = new Properties()
    props.put("bootstrap.servers", "sparke3:6667")
    //props.put("client.id", "ScalaProducerExample")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    //println("The following is being ingested to Kafka")
    // println(message)
    val producer = new KafkaProducer[String, String](props)

  def startProducing(numberOfMessage:Int):Unit ={

    for(i <- 0 to numberOfMessage){
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
      val message = newLine.mkString("\t")
      val data = new ProducerRecord[String,String]("ETL","1",message)
      producer.send(data)
    }

    }
  def stopProducer():Unit = {
    producer.close()

  }
}

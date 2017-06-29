import java.util.Properties
import kafka.serializer.StringDecoder
import org.apache.kafka.clients.producer.{ProducerRecord, KafkaProducer}
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.io.Source
import scala.util.Random

/**
  * Created by ctam on 6/23/17.
  */
object kafkaexecrise {
  def main(args: Array[String])= {

    val producer = new FileReader()
    val consumer = new ChunConsumer()
    consumer.startConsuming()
    producer.startProducing(10)
    producer.stopProducer()


  }
}

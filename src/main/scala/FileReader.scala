/**
  * Created by Bozis on 6/19/2017.
  */

import scala.io.Source
import scala.util.Random

object ChunFileReader {
  def main(args: Array[String])={

    val logFile = "D:\\IdeaProjects\\text\\src\\main\\resources\\omniture-logs.tsv"
    //val products = "D:\\IdeaProjects\\text\\src\\main\\resources\\products.tsv"
   // val users = "D:\\IdeaProjects\\text\\src\\main\\resources\\users.tsv"

    val logFileSource = Source.fromFile(logFile).getLines().toList
    val fileSize = logFileSource.size


    for(i <- 1 to 1){
      val random = new Random()
      val lineList = logFileSource(random.nextInt(fileSize)).split("\t")
      val replacementLineList = logFileSource(random.nextInt(fileSize)).split("\t")
      var newLine = Array[String]()

      var columnNumber = random.nextInt(lineList.size)
      var finish = false

      while (finish != true ){
        if (replacementLineList(columnNumber) != lineList(columnNumber))
          {
           // println("the column value is :$" + replacementLineList(columnNumber) + ", The id is :$" + lineList(0) + ", The replaced value is :$" + lineList(columnNumber))
            newLine = lineList.updated(columnNumber,replacementLineList(columnNumber))
            finish = true
          }
        columnNumber = random.nextInt(lineList.size)
      }

     // println(lineList(columnNumber))
      //println(replacementLineList(columnNumber))
      //println(newLine(columnNumber))

      println(newLine.mkString("\t"))

      //  println(lineList)
      //  println(replacementLineList)
    }



  }

}

package Anish

import java.io.{FileNotFoundException, PrintWriter}
import scala.io.Source
import scala.util.{Using, Try}

class UrlExtractor(inputFile: String, outputFile: String) {

  private val urlPattern = """\b(https?)://\S+\b""".r

  def readAndWriteUrls(): Unit = {
    Try {
      Using.resource(Source.fromFile(inputFile)) { source =>
        // val urls = source.getLines().flatMap(line => urlPattern.findAllMatchIn(line).map(_.group(0))).toList
        val urls = source.getLines().flatMap(line => urlPattern.findAllIn(line)).toList

        Using.resource(new PrintWriter(outputFile)) { writer =>
          urls.foreach(url => writer.println(url)) // writes in a new line
        }

        println(s"Extracted URLs written to $outputFile")
      }
    } recover {
      case e: FileNotFoundException => println(s"File not found: ${e.getMessage}")
      case ex: Exception => println(s"An error occurred: ${ex.getMessage}")
    }
  }
}

//object UrlExtractor extends App {
//
//  val inputFile = "src/Input"
//  val outputFile = "src/Output"
//
//  val urlExtractor = new UrlExtractor(inputFile, outputFile)
//  urlExtractor.readAndWriteUrls()
//  println("Done")
//}

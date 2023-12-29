package Anish

import org.scalatest.funsuite.AnyFunSuite

import java.io.File
import scala.util.Using

class UrlExtractorSuite extends AnyFunSuite {

  test("UrlExtractor should extract and write URLs to the output file") {
    val inputFile = "src/input" // Create a test input file
    val outputFile = "src/output" // Temporary output file for testing

    Using.resource(new java.io.PrintWriter(inputFile)) { writer =>
      writer.write("Text with URLs: https://example.com and http://anotherexample.com")
    }

    val urlExtractor = new UrlExtractor(inputFile, outputFile) // Instantiate UrlExtractor

    urlExtractor.readAndWriteUrls() // Call readAndWriteUrls() method

    // Verify the expected output using Using.resource for reading the file
    Using.resource(scala.io.Source.fromFile(outputFile)) { source =>
      val expectedOutput = List("https://example.com", "http://anotherexample.com")
      val actualOutput = source.getLines().toList
      assert(actualOutput == expectedOutput)
    }

    // Clean up temporary files (optional)
    val tempInputFile = new File(inputFile)
    val tempOutputFile = new File(outputFile)
    tempInputFile.delete()
    tempOutputFile.delete()
  }
}

package scrambled

import java.io.{File, PrintWriter}

import scala.util.Try

trait ScramblerMock {

  val dictionaryFilePath = "dictionary.txt"
  val inputFilePath = "input.txt"

  def clearUp() : Unit = {
    Seq(dictionaryFilePath, inputFilePath).foreach(path => Try(new File(path).delete()))
  }

  val writeDictionaryFile: Seq[String] => Unit = write(dictionaryFilePath)

  val writeInputFile : Seq[String] => Unit = write(inputFilePath)

  def write(path : String)(lines : Seq[String]) : Unit = {
    val writer = new PrintWriter(new File(path))
    writer.write(lines.mkString("\n"))
    writer.close()
  }

}

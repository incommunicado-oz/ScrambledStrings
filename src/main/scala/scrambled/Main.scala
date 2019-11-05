package scrambled

import com.typesafe.scalalogging.LazyLogging

import scala.annotation.tailrec
import scala.util.{Failure, Success, Try}

object Main extends LazyLogging {


  def main(args: Array[String]): Unit = {

    val result = for {
      config <- Config(args)
      wordCount <- scrambledString(config)
    } yield wordCount

    result.getOrElse(Seq.empty[String]).foreach(line => logger.info(line))
  }

  def scrambledString(config: Config): Option[Seq[String]] = {
    Try(Dictionary.readDictionary(config.dictionaryPath)) match {
      case Success(dictionary) =>
        Read.fromFile[Seq[String]](config.inputPath) { it =>
          it
            .map(line => countWords(line.toLowerCase, dictionary))
            .zipWithIndex
            .map {
              case (wordCount, index) =>
                val pageNumber = index + 1
                s"Case #$pageNumber: $wordCount"
            }.toSeq
        }
      case Failure(exception) =>
        logger.error(s"Failed to load Dictionary", exception)
        None
    }
  }

  @tailrec
  def countWords(line: String, dictionary: Dictionary, wordsFound: Set[String] = Set.empty[String]): Int = {
    def findWordInLine: Set[String] = {
      dictionary.wordSizes.map { wordSize =>
        line.grouped(wordSize).foldLeft(wordsFound) { (acc, word) =>
          acc ++ dictionary.findWord(word)
        }
      }.foldLeft(Set.empty[String])(_ union _)
    }

    if (line.isEmpty)
      wordsFound.size
    else {
      countWords(line.tail, dictionary, findWordInLine)
    }
  }
}

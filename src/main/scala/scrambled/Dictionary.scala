package scrambled

import com.typesafe.scalalogging.LazyLogging

import scala.util.{Failure, Success, Try}

case class Dictionary(dict : Map[String, Set[String]]) {
  require(Dictionary.containsValidWord(this), s"Dictionary words must be between ${Dictionary.wordSizeMin} and ${Dictionary.wordSizeMax}")
  require(dict.values.flatten.map(_.length).sum <= Dictionary.wordSizeMax, s"Sum of all words must not be greater than ${Dictionary.wordSizeMax}")

  lazy val wordSizes: Set[Int] = Set(dict.values.map(_.head.length).toSeq :_*)

  def findWord(scrambledWord : String) : Set[String] = {
    val sortedScrambled = scrambledWord.toSeq.sorted
    dict.getOrElse(Dictionary.createKey(scrambledWord), Set.empty[String]).filter(_.toSeq.sorted == sortedScrambled)
  }
}

object Dictionary extends LazyLogging{


  val wordSizeMin = 2
  val wordSizeMax = 105

  def createKey(word : String) : String = {
    s"${word.take(1)}${word.takeRight(1)}${word.length}"
  }

  def containsValidWord(dictionary : Dictionary) : Boolean = {

    dictionary.dict.values.flatten.foldLeft(List.empty[String]) { (acc, word) =>
      word match {
        case w if w.length < wordSizeMin =>
          logger.warn(s"$w is below $wordSizeMin limit")
          w :: acc
        case w if w.length > wordSizeMax =>
          logger.warn(s"$w is above $wordSizeMax limit")
          w :: acc
        case _ => acc
      }
    }.isEmpty
  }

  def readDictionary(path : String) : Dictionary = {
    val dict = Read.fromFile(path) { lines =>
      lines.map(_.toLowerCase)
        .foldLeft(Map[String, Set[String]]()) { (acc, word) =>
          acc.updated(createKey(word), acc.getOrElse(createKey(word), Set.empty[String]) + word)
        }
    }.getOrElse(Map.empty[String, Set[String]])
    Dictionary(dict)
  }

}

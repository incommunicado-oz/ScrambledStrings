package scrambled

import org.scalatest.{BeforeAndAfter, BeforeAndAfterEach, FlatSpec, Matchers}
import scrambled.{Config, Main}

import scala.util.Random

class ScramblerSpec extends FlatSpec with Matchers with BeforeAndAfterEach with ScramblerMock {

  override def beforeEach() {
    clearUp()
  }

  override def afterEach() {
    clearUp()
  }

  trait Fixture{
    val config = Config(dictionaryFilePath, inputFilePath)

    val scramble = (s :String) => s"${s.head}${Random.shuffle(s.tail.init)}${s.last}"

  }

  behavior of "Scrambler"

  it should "count the words from the dictonary file which is in the input" in new Fixture {
    val dictionary: Seq[String] = Seq("axpaj", "apxaj", "dnrbt", "pjxdn", "abd", "zz")
    val input: Seq[String] = Seq(
      "aapxjdnrbtvldptfzbbdbbzxtndrvjblnzjfpvhdhhpxjdnrbt",
      "aapxjdnrbtvldptfzbbdbbzxtndrvjblnzjfpvhdhhpxjdnrbt",
      "paaxx",
      "",
      "a",
      "abd",
      "zzzz")

    writeDictionaryFile(dictionary)
    writeInputFile(input)
    val expected = Seq("Case #1: 4", "Case #2: 4", "Case #3: 0","Case #4: 0","Case #5: 0", "Case #6: 1", "Case #7: 1")
    Main.scrambledString(config).getOrElse(Seq.empty[String]) should contain theSameElementsAs expected
  }

  it should "handle a big words from the dictionary" in new Fixture {
    val bigWord = Random.alphanumeric.filter(_.isLetter).take(105).mkString
    val dictionary: Seq[String] = Seq(bigWord)
    val input: Seq[String] = Seq(
      s"${scramble(bigWord)}${scramble(bigWord)}${scramble(bigWord)}",
      "jdjdsjsdlsd",
    s"${scramble(bigWord)}${scramble(bigWord)}")

    writeDictionaryFile(dictionary)
    writeInputFile(input)
    val expected = Seq("Case #1: 1", "Case #2: 0", "Case #3: 1")
    Main.scrambledString(config).getOrElse(Seq.empty[String]) should contain theSameElementsAs expected
  }

  it should "deal with empty input files " in new Fixture {
    writeDictionaryFile(Seq.empty[String])
    writeInputFile(Seq.empty[String])
    Main.scrambledString(config) shouldBe Some(Seq.empty[String])
  }

}

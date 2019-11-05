package scrambled

import org.scalatest._
import scrambled.Dictionary

import scala.util.{Failure, Random}

class ReadSpec extends FlatSpec with Matchers with BeforeAndAfterEach with ScramblerMock {

  override def beforeEach() {
    clearUp()
  }

  override def afterEach() {
    clearUp()
  }

  trait Fixture{

  }

  behavior of "A word Dictionary"

  it should "read in a read all words from a dictionary file" in new Fixture {
    val dict: Seq[String] = Seq("axpaj", "aaadj", "DNT")
    writeDictionaryFile(dict)
    val expected = Dictionary(Map[String, Set[String]]("aj5" -> Set("axpaj", "aaadj"), "dt3" -> Set("dnt") ))
    //Dictionary.readDictionary(dictionaryFilePath) shouldBe(expected)
  }

  it should "Reject a words which are below or above the limitation" in new Fixture {
    the [IllegalArgumentException] thrownBy {
      Dictionary(Map[String, Set[String]]("test" -> Set("a")))
    } should have message ("requirement failed: Dictionary words must be between 2 and 105")


    the [IllegalArgumentException] thrownBy {
      Dictionary(Map[String, Set[String]]("test" -> Set(Random.alphanumeric.take(200).mkString)))
    } should have message ("requirement failed: Dictionary words must be between 2 and 105")
  }

  it should "Reject a situation where the sum length is too much" in new Fixture {
    writeDictionaryFile(Random.alphanumeric.take(200).grouped(5).map(_.mkString).toSeq)
    the [IllegalArgumentException] thrownBy {
      Dictionary.readDictionary(dictionaryFilePath)
    } should have message ("requirement failed: Sum of all words must not be greater than 105")
  }
}

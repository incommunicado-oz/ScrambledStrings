package scrambled

import org.scalatest.{BeforeAndAfter, BeforeAndAfterEach, FlatSpec, Matchers}
import scrambled.Config

class ConfigSpec extends FlatSpec with Matchers with BeforeAndAfterEach with ScramblerMock {

  override def beforeEach() {
    clearUp()
  }

  override def afterEach() {
    clearUp()
  }

  behavior of "Config"

  it should "parse the required arguments" in {
    writeDictionaryFile(Seq.empty[String])
    writeInputFile(Seq.empty[String])
    val expected = Some(Config(dictionaryFilePath, inputFilePath))
    Config(Array("--dictionary", dictionaryFilePath, "--input", inputFilePath)) shouldBe expected
  }

  it should "fail arguments which are pointing to a non existing file" in {
    val expected = Config(dictionaryFilePath, inputFilePath)
    Config(Array("--dictionary", dictionaryFilePath, "--input", inputFilePath)) shouldBe None
  }

}

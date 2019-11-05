package scrambled

import java.io.File

import scopt.OptionParser

case class Config(dictionaryPath : String, inputPath : String)

object Config {

  val parser: OptionParser[Config] = new scopt.OptionParser[Config]("Scrambled-Strings") {
    opt[String]('d', "dictionary")
      .required()
      .text("The file path of the dictionary file")
      .validate(doesFileExist)
      .action((path, config) => config.copy(dictionaryPath = path))

    opt[String]('i', "input")
      .required()
      .text("The file path of the dictionary file")
      .validate(doesFileExist)
      .action((path, config) => config.copy(inputPath = path))
  }

  def apply(args : Array[String]): Option[Config] = {
    parser.parse(args, Config("", ""))
  }

  def doesFileExist : String => Either[String, Unit] = {
    path =>
      if(new File(path).isFile){
       Right()
    } else {
        Left(s"""File $path does not exist""")
      }
  }

}

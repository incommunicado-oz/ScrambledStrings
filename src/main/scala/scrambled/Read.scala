package scrambled

import java.util.Dictionary

import com.typesafe.scalalogging.LazyLogging

import scala.io.Source
import scala.util.{Failure, Success, Try}

object Read extends LazyLogging {

  def fromFile[A](path: String)(f: Iterator[String] => A): Option[A] = {
      val source = Source.fromFile(path)
    val result = Try(f(source.getLines()))
    source.close()
    result match {
      case Success(value) => Some(value)
      case Failure(exception) =>
        logger.error(s"Failed to open ${path}", exception)
        None
    }
  }
}

package io.jump.suite

import java.io.File

import scala.io.Source

trait ContentMatcher {
  protected val content: List[String]

  def matchFields(s: String): Option[String] = {
    try {
      Some(content.filter(_.contains(s)).mkString.split(":").toList.tail(0))
    } catch {
      case e: IndexOutOfBoundsException => None
    }
  }
}

abstract class Content extends ContentMatcher {

  def getTags(tagsType: String): List[String] = matchFields(tagsType + " tags" + ":") match {
    case Some(i) => i.split(",").toList.map(_.replace(" ", ""))
    case None => List()
  }

  def getDoc(docType: String): String = matchFields(docType + ":") match {
    case Some(i) => if (i(0) == ' ') i.tail else i
    case None => ""
  }
}

case class Suite(private val f: File) extends Content {

  override protected val content = Source.fromFile(f).getLines().toList

  val name = f.getName

  val tags = getTags("Suite")

  val doc = getDoc("Documentation")

}

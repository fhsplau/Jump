package io.jump.suite

import java.io.File

import scala.io.Source

trait ContentMatcher {
  protected val content: List[String]

  def matchFields(s: String): List[String] = content.filter(_.contains(s)).mkString.split(":").toList
}

abstract class Content extends ContentMatcher{

  def extractTags(tagsType: String): List[String] =
    matchFields(tagsType+" tags" + ":").tail(0).split(",").toList.map(_.replace(" ", ""))

  def extractDocumentation(docType: String): String = {
    val doc = matchFields(docType+":").tail(0)
    if(doc(0)==' ') doc.tail else doc
  }
}

case class Suite(private val f: File) extends Content {

  override protected val content = Source.fromFile(f).getLines().toList

  val name = f.getName

  val tags = extractTags("Suite")

  val doc = extractDocumentation("Documentation")

}

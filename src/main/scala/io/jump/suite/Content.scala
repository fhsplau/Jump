package io.jump.suite

import io.jump.suite.Enums.Doc._
import io.jump.suite.Enums.Tags._

sealed trait Separators {
  val FIELD_SEPARATOR = ":"
  val TAG_SEPARATOR = ","
}

sealed trait ContentMatcher extends Separators{
  protected val content: List[String]

  def matchFields(s: String): Option[String] = {
    try {
      Some(content.filter(_.contains(s)).mkString.split(FIELD_SEPARATOR).toList.tail.head)
    } catch {
      case e: IndexOutOfBoundsException => None
      case e: NoSuchElementException => None
    }
  }

  def getTags(tagsType: Tags): List[String] = matchFields(tagsType.toString + FIELD_SEPARATOR) match {
    case Some(i) => i.split(TAG_SEPARATOR).toList.map(_.replace(" ", ""))
    case None => List()
  }
}

//  Get or else
abstract class Content extends ContentMatcher {

  val name: String
  val tags: List[String]

  def getDoc(docType: Doc): String = matchFields(docType.toString + FIELD_SEPARATOR) match {
    case Some(i) => if (i.head == ' ') i.tail else i
    case None => ""
  }
}


package io.jump.suite

sealed trait Separators {
  val FIELD_SEPARATOR = ":"
  val TAG_SEPARATOR = ","
  val WHITE_SPACE = " "
  val EMPTY = ""
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

  def getTags(tagsType: String): List[String] = matchFields(tagsType + FIELD_SEPARATOR) match {
    case Some(i) => i.split(TAG_SEPARATOR).toList.map(_.replace(WHITE_SPACE, EMPTY))
    case None => List()
  }
}

//  Get or else
abstract class Content extends ContentMatcher {

  val name: String
  val tags: List[String]

  def getDoc(docType: String): String = matchFields(docType + FIELD_SEPARATOR) match {
    case Some(i) => if (i.head.toString == WHITE_SPACE) i.tail else i
    case None => ""
  }
}


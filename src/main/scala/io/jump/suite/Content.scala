package io.jump.suite

sealed trait ContentMatcher {
  protected val content: List[String]

  def matchFields(s: String): Option[String] = {
    try {
      Some(content.filter(_.contains(s)).mkString.split(":").toList.tail.head)
    } catch {
      case e: IndexOutOfBoundsException => None
      case e: NoSuchElementException => None
    }
  }

  def getTags(tagsType: String): List[String] = matchFields("@" + tagsType + " tags" + ":") match {
    case Some(i) => i.split(",").toList.map(_.replace(" ", ""))
    case None => List()
  }
}

//  Get or else
abstract class Content extends ContentMatcher {

  val name: String
  val tags: List[String]

  def getDoc(docType: String): String = matchFields("@" + docType + ":") match {
    case Some(i) => if (i.head == ' ') i.tail else i
    case None => ""
  }
}

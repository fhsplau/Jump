package io.jump.suite

sealed trait Common {
  val FIELD_POINTER = "@"
  val FIELD_SEPARATOR = ":"
  val TAG_SEPARATOR = ","
  val WHITE_SPACE = " "
  val EMPTY_STRING = ""
}

sealed trait Tags extends Common {

  private val TAG_FIELD_END = " tags" + FIELD_SEPARATOR

  val SUITE_TAG = FIELD_POINTER + "Suite" + TAG_FIELD_END
  val TEST_TAG = FIELD_POINTER + "Test" + TAG_FIELD_END
}

sealed trait Doc extends Common {
  val SUITE_DOC = FIELD_POINTER + "Documentation"
  val TEST_DOC = FIELD_POINTER + "Test scenario"
}

sealed trait TestScenario extends Common {
  val TEST_SCENARIO = FIELD_POINTER + "Test scenario:"
}

sealed trait ContentMatcher extends Common{
  protected val content: List[String]

  def matchFields(s: String): Option[String] = {
    try {
      Some(content.filter(_.contains(s)).mkString.split(FIELD_SEPARATOR).toList.tail.head)
    } catch {
      case e: IndexOutOfBoundsException => None
      case e: NoSuchElementException => None
    }
  }

  def getTags(tagsType: String): List[String] = matchFields(tagsType) match {
    case Some(i) => i.split(TAG_SEPARATOR).toList.map(_.replace(WHITE_SPACE, EMPTY_STRING))
    case None => List()
  }
}

//  Get or else
abstract class Content extends ContentMatcher with Tags with Doc with TestScenario{

  val name: String
  val tags: List[String]

  def getDoc(docType: String): String = matchFields(docType + FIELD_SEPARATOR) match {
    case Some(i) => if (i.head.toString == WHITE_SPACE) i.tail else i
    case None => EMPTY_STRING
  }
}


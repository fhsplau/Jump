package io.jump.suite

sealed trait Separators {
  val FIELD_SEPARATOR = ":"
  val TAG_SEPARATOR = ","
  val WHITE_SPACE = " "
  val EMPTY_STRING = ""
}

sealed trait Common {
  val FIELD = "@"
}

sealed trait Tags extends Common {

  private val TAG_FIELD_END = " tags"

  val SUITE_TAG = FIELD + "Suite" + TAG_FIELD_END
  val TEST_TAG = FIELD + "Test" + TAG_FIELD_END
}

sealed trait Doc extends Common {
  val SUITE_DOC = FIELD + "Documentation"
  val TEST_DOC = FIELD + "Test scenario"
}

sealed trait TestScenario extends Common {
  val TEST_SCENARIO = FIELD + "Test scenario:"
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


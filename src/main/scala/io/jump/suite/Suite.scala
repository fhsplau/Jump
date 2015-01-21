package io.jump.suite

import java.io.File

import scala.io.Source

trait ContentMatcher {
  protected val content: List[String]

  def matchFields(s: String): Option[String] = {
    try {
      Some(content.filter(_.contains(s)).mkString.split(":").toList.tail.head)
    } catch {
      case e: IndexOutOfBoundsException => None
      case e: NoSuchElementException => None
    }
  }
}

//  Get or else
abstract class Content extends ContentMatcher {

  val name: String
  val tags: List[String]
  val doc: String

  def getTags(tagsType: String): List[String] = matchFields(tagsType + " tags" + ":") match {
    case Some(i) => i.split(",").toList.map(_.replace(" ", ""))
    case None => List()
  }

  def getDoc(docType: String): String = matchFields(docType + ":") match {
    case Some(i) => if (i.head == ' ') i.tail else i
    case None => ""
  }
}

case class Test(content: List[String]) extends Content {
  override val name: String = ""
  override val doc: String = ""
  override val tags: List[String] = List()
}

case class Suite(private val f: File) extends Content {

  override protected val content = Source.fromFile(f).getLines().toList

  override val name = f.getName

  override val tags = getTags("Suite")

  override val doc = getDoc("Documentation")

  val tests = {
    def findTests(l: List[String], acc: List[Int], currLine: Int): List[Int] =
      if (l.isEmpty) acc
      else findTests(
        l.tail,
        if (l.head.contains("Test")) acc ::: List(currLine) else acc,
        currLine + 1
      )

    def getTests(testIndexes: List[Int]): List[Test] = {
      if (testIndexes.size == 1) List(new Test(getTest(testIndexes.head, content.size)))
      else List(new Test(getTest(testIndexes.head, testIndexes(1)))) ::: getTests(testIndexes.tail)
    }

    def getTest(b: Int, e: Int): List[String] =
      if (b >= e) List()
      else List(content(b).replace("  ", "")) ::: getTest(b + 1, e)

    getTests(findTests(content, List(), 0))
  }

}

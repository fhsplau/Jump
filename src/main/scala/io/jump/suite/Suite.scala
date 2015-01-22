package io.jump.suite

import java.io.{File, FileNotFoundException}

import scala.io.Source

// TODO create deleteWhiteSpaces method
// TODO read and think about scala's futures


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

trait SuiteContent {
  protected val f: File

  val tests: List[Test]

  protected def getContent(path: String): List[String] = try {
    Source.fromFile(f).getLines().toList
  } catch {
    case e: FileNotFoundException => throw new Exception("[error] File not found")
  }
}
//  Get or else
abstract class Content extends ContentMatcher {

  val name: String
  val tags: List[String]
  val doc: String


  def getTags(tagsType: String): List[String] = matchFields("@" + tagsType + " tags" + ":") match {
    case Some(i) => i.split(",").toList.map(_.replace(" ", ""))
    case None => List()
  }

  def getDoc(docType: String): String = matchFields("@" + docType + ":") match {
    case Some(i) => if (i.head == ' ') i.tail else i
    case None => ""
  }
}

case class Test(content: List[String]) extends Content {
  override val name: String = ""
  override val doc: String = ""
  override val tags: List[String] = List()
}

case class Suite(private val path: String) extends Content with SuiteContent {

  override val f = new File(path)

  override protected val content = getContent(path)

  override val name: String = f.getName

  override val tags: List[String] = getTags("Suite")

  override val doc: String = getDoc("Documentation")

  override val tests: List[Test] = {
    def findTests(lines: List[String], acc: List[Int], currLine: Int): List[Int] =
      if (lines.isEmpty) acc
      else findTests(
        lines.tail,
        if (lines.head.contains("@Test")) acc ::: List(currLine) else acc,
        currLine + 1
      )

    def getTests(indexes: List[Int]): List[Test] = indexes match {
      case i if i.isEmpty => List()
      case i if i.size == 1 => List(createTest(i.head, content.size))
      case _ => List(createTest(indexes.head, indexes(1))) ::: getTests(indexes.tail)
    }

    def createTest(a: Int, b: Int): Test = new Test(getTest(a, b))

    def getTest(b: Int, e: Int): List[String] =
      if (b >= e) List()
      else List(content(b).replace("  ", "")) ::: getTest(b + 1, e)

    getTests(findTests(content, List(), 0))
  }

}

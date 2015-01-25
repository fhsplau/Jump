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

trait SuiteContent extends Content {
  protected val f: File

  val doc: String
  val tests: List[Test]

  protected def getContent(path: String): List[String] = try {
    Source.fromFile(f).getLines().toList
  } catch {
    case e: FileNotFoundException => throw new Exception("[error] File not found")
  }
}

trait TestContent extends Content {
  val steps: List[String]
}

case class Test(content: List[String]) extends TestContent {
  override val name: String = getDoc("Test scenario")

  override val tags: List[String] = getTags("Test")

  override val steps: List[String] = {
    def getSteps(l: List[String]): List[String] =
      if (l.isEmpty) List()
      else (if (l.head.contains("@")) List() else List(l.head)) ::: getSteps(l.tail)
    getSteps(content)
  }
}

case class Suite(private val path: String) extends SuiteContent {

  override val f = new File(path)

  override val name: String = f.getName

  override protected val content = getContent(path)

  override val tags: List[String] = getTags("Suite")

  override val doc: String = getDoc("Documentation")

  override val tests: List[Test] = {
    def createTest(a: Int, b: Int): Test = new Test(getTest(a, b))

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

    def getTest(b: Int, e: Int): List[String] =
      if (b >= e) List()
      else List(content(b).replace("  ", "")) ::: getTest(b + 1, e)

    getTests(findTests(content, List(), 0))
  }

}

package io.jump.suite

import java.io.{File, FileNotFoundException}
import Enums._
import scala.io.Source

// TODO create deleteWhiteSpaces method
// TODO read and think about scala's futures

sealed trait SuiteContent extends Content {
  protected val f: File

  val doc: String
  val tests: List[Test]

  protected def getContent(path: String): List[String] = try {
    Source.fromFile(f).getLines().toList
  } catch {
    case e: FileNotFoundException => throw new Exception("[error] File not found")
  }
}

case class Suite(private val path: String) extends SuiteContent {

  override val f = new File(path)

  override val name: String = f.getName

  override protected val content = getContent(path)

  override val tags: List[String] = getTags(Tags.SUITE)

  override val doc: String = getDoc(Doc.SUITE)

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

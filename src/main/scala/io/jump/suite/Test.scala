package io.jump.suite

import io.jump.suite.Enums._

sealed trait TestContent extends Content {
  val steps: List[Step]
}

case class Test(private val testList: List[String]) extends TestContent {
  override protected val content = testList

  override val name: String = getDoc(Doc.TEST_DOC)

  override val tags: List[String] = getTags(Tags.TEST_TAG)

  override val steps: List[Step] = {
    def getSteps(l: List[String]): List[Step] =
      if (l.isEmpty) List()
      else (
        if (l.head.contains(Common.FIELD) || l.head.size <= 1) List()
        else List(new Step(l.head))
        ) ::: getSteps(l.tail)
    getSteps(content)
  }
}

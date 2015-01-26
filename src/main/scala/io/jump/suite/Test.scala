package io.jump.suite

sealed trait TestContent extends Content {
  val steps: List[String]
}

case class Test(private val testList: List[String]) extends TestContent {
  override protected val content = testList

  override val name: String = getDoc("Test scenario")

  override val tags: List[String] = getTags("Test")

  override val steps: List[String] = {
    def getSteps(l: List[String]): List[String] =
      if (l.isEmpty) List()
      else (if (l.head.contains("@")) List() else List(l.head)) ::: getSteps(l.tail)
    getSteps(content)
  }
}

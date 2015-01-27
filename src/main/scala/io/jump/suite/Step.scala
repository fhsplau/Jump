package io.jump.suite

case class Step(private val step: String) {

  private val stepList = step.split(" ").map(_.replace(" ", "")).filter(_.size > 0)

  val tag: String = stepList(0)

  val name: String = stepList.tail.mkString(" ")

}

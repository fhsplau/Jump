package io.jump.suite

case class Step(step: String) {

  val tag:String = step.split(" ")(0)

  val name:String = step.split(" ").tail.mkString(" ")

}

package io.jump.suite

case class Step(step: String) {

  val tag:String = step.split(" ")(0)

}

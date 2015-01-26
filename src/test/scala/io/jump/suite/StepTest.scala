package io.jump.suite

import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class StepTest extends FunSuite with BeforeAndAfter{

  var step: Step = _

  before {
    step = new Step("Given my name is Piotr")
  }

  test("proper step's tag") {
    assert(step.tag === "Given")
  }

  test("proper step's name") {
    assert(step.name === "my name is Piotr")
  }
}

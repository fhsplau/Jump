package io.jump.suite
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestClassTests extends FunSuite{

  val aTest = new Test(List("@Test scenario: Print greetings with my name",
    "@Test tags: another, pp",
    "Given my name is Piotr",
    "And my surname is Kacprzak",
    "When I execute my method",
    "Then I should see Hello Piotr Kacprzak"))

  val aTest2= new Test(List("@Test scenario: Print greetings with my name",
    ""))

  val steps = List(new Step("Given my name is Piotr"),
    new Step("And my surname is Kacprzak"),
    new Step("When I execute my method"),
    new Step("Then I should see Hello Piotr Kacprzak"))

  test("proper test name") {
    assert(aTest.name === "Print greetings with my name")
  }

  test("proper tags") {
    assert(aTest.tags === List("another", "pp"))
  }

  test("proper steps") {
    assert(aTest.steps === steps)
  }

  test("step as empty line") {
    assert(aTest2.steps === List())
  }

  test("only field in test no steps") {
    val aTest3 = new Test(List("@Test scenario: Print greetings with my name"))
    assert(aTest3.steps === List())
  }

  test("empty list"){
    val aTest3 = new Test(List())

    assert(aTest3.steps === List())
  }

}

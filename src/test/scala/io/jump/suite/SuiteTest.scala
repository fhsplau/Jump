package io.jump.suite

import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.junit.JUnitRunner
import java.io.File

@RunWith(classOf[JUnitRunner])
class SuiteTest extends FunSuite with BeforeAndAfter {

  var suite: Suite = _
  var suite2: Suite = _
  var suite3: Suite = _
  val path = "/Users/piotr.kacprzak/svn/Jump/src/test/BDD"
  val name = "my_bdd_test.suite"
  val name2 = "my_bdd_test2.suite"
  val name3 = "pref_bdd_test.suite"
  val withoutTests = "suite_without_tests.suite"
  val suiteFile = new File(path + "/" + name)
  val suiteFile2 = new File(path + "/" + name2)
  val suiteFile3 = new File(path + "/" + name3)
  val suiteTags = List("new", "ok")
  val suiteDoc = "This is my documentation"
  val tests = List(new Test(List("@Test scenario: Print greetings with my name",
    "Given my name is Piotr",
    "And my surname is Kacprzak",
    "When I execute my method",
    "Then I should see Hello Piotr Kacprzak")))

  before {
    suite = new Suite(suiteFile)
    suite2 = new Suite(suiteFile2)
  }

  test("suite name is known") {
    assert(suite.name === name)

  }

  test("documentation") {
    assert(suite.doc === suiteDoc)
  }

  test("check tags") {
    assert(suite.tags === suiteTags)
  }

  test("no documentation") {
    assert(suite2.doc === "")
  }

  test("no tags") {
    assert(suite2.tags === List())
  }

  test("list with tests") {
    assert(suite.tests === tests)
  }

  ignore("test bigfile") {
    suite3 = new Suite(suiteFile3)
    assert(suite3.tests.size === 2784)
  }
  
  test("suite without tests") {
    val suiteWithoutTests = new Suite(new File(path+"/"+withoutTests))
    assert(suiteWithoutTests.tests === List())
  }

  test("only one test if word 'test' is part of the step") {
    val suiteWithTwoTests = new Suite(new File(path+"/"+"two_tests.suite"))
    assert(suiteWithTwoTests.tests.size === 2)
  }

  test("suite's fields without '@'") {
    val suiteWithoutAt = new Suite(new File(path+"/"+"suite_without_at.suite"))
    assert(suiteWithoutAt.doc === "")
    assert(suiteWithoutAt.tags === List())
    assert(suiteWithoutAt.tests === List())
  }

}

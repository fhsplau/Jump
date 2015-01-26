package io.jump.suite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, FunSuite}

@RunWith(classOf[JUnitRunner])
class SuiteTest extends FunSuite with BeforeAndAfter {

  var suite: Suite = _
  var suite2: Suite = _
  val path = "/Users/piotr.kacprzak/svn/Jump/src/test/BDD"
  val name = "my_bdd_test.suite"
  val name2 = "my_bdd_test2.suite"
  val suiteTags = List("new", "ok")
  val suiteDoc = "This is my documentation"
  val tests = List(new Test(List("@Test scenario: Print greetings with my name",
    "Given my name is Piotr",
    "And my surname is Kacprzak",
    "When I execute my method",
    "Then I should see Hello Piotr Kacprzak")))

  before {
    suite = new Suite(path + "/" + name)
    suite2 = new Suite(path + "/" + name2)
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
    val suite3 = new Suite(path + "/" + "pref_bdd_test.suite")
    assert(suite3.tests.size === 2784)
  }

  test("suite without tests") {
    val suiteWithoutTests = new Suite(path + "/" + "suite_without_tests.suite")
    assert(suiteWithoutTests.tests === List())
  }

  test("only one test if word 'test' is part of the step") {
    val suiteWithTwoTests = new Suite(path + "/" + "two_tests.suite")
    assert(suiteWithTwoTests.tests.size === 2)
  }

  test("suite's fields without '@'") {
    val suiteWithoutAt = new Suite(path + "/" + "suite_without_at.suite")
    assert(suiteWithoutAt.doc === "")
    assert(suiteWithoutAt.tags === List())
    assert(suiteWithoutAt.tests === List())
  }

  test("non existing suite") {
    withClue("[error] File not found") {
      intercept[Exception] {
        new Suite(path + "/" + "non_existing_suite.suite")
      }
    }

  }

}

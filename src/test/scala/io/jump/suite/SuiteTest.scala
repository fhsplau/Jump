package io.jump.suite

import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.junit.JUnitRunner
import java.io.File

@RunWith(classOf[JUnitRunner])
class SuiteTest extends FunSuite with BeforeAndAfter {

  var suite: Suite = _
  var suite2: Suite = _
  val path = "/Users/piotr.kacprzak/svn/Jump/src/test/BDD"
  val name = "my_bdd_test.suite"
  val name2 = "my_bdd_test2.suite"
  val suiteFile = new File(path + "/" + name)
  val suiteFile2 = new File(path+"/"+name2)
  val suiteTags = List("@new", "@ok", "@jira")
  val suiteDoc = "This is my documentation"

  before {
    suite = new Suite(suiteFile)
    suite2 = new Suite(suiteFile2)
  }

  test("suite name is known") {
    assert(suite.name === name)

  }

  test("documentation"){
    assert(suite.doc === suiteDoc)
  }

  test("check tags") {
    assert(suite.tags === suiteTags)
  }

  ignore("no documentation"){
    assert(suite2.doc === None)
  }

}

package io.jump.suite

sealed trait Common {
  val FIELD = "@"
}

sealed trait Tags extends Common {

  private val TAG_FIELD_END = " tags"

  val SUITE_TAG = FIELD + "Suite" + TAG_FIELD_END
  val TEST_TAG = FIELD + "Test" + TAG_FIELD_END
}

sealed trait Doc extends Common {
  val SUITE_DOC = FIELD + "Documentation"
  val TEST_DOC = FIELD + "Test scenario"
}

sealed trait Test extends Common {
  val TEST_SCENARIO = FIELD + "Test scenario:"
}

sealed abstract class Fields extends Tags with Doc with Test

object Properties extends Fields

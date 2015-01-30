package io.jump.suite

object Enums {

  object Common {

    val FIELD = "@"
    val TEST_SCENARIO = FIELD+"Test scenario:"
  }

  object Tags {

    private val TAG_FIELD_END = " tags"

    val SUITE_TAG = "@Suite" + TAG_FIELD_END
    val TEST_TAG = "@Test" + TAG_FIELD_END
  }

  object Doc {
    val SUITE_DOC = "@Documentation"
    val TEST_DOC = "@Test scenario"
  }

}

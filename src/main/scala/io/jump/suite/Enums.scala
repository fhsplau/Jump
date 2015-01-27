package io.jump.suite

object Enums{

  object Tags extends Enumeration {
    type Tags = Value

    private val TAG_FIELD_END = " tags"

    val SUITE = Value("@Suite"+TAG_FIELD_END)
    val TEST = Value("@Test"+TAG_FIELD_END)
  }

  object Doc extends Enumeration {
    type Doc = Value

    val SUITE = Value("@Documentation")
    val TEST = Value("@Test scenario")
  }
}

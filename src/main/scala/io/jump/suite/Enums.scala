package io.jump.suite

object Enums{

  object Tags extends Enumeration {
    type Tags = Value

    val SUITE = Value("@Suite")
    val TEST = Value("@Test")
  }

  object Doc extends Enumeration {
    type Doc = Value

    val SUITE = Value("@Documentation")
    val TEST = Value("@Test scenario")
  }
}

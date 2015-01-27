package io.jump.suite

object Enums{

  object Tags extends Enumeration {
    type Tags = Value

    val SUITE = Value("@Suite")
    val TEST = Value("@Test")
  }
}

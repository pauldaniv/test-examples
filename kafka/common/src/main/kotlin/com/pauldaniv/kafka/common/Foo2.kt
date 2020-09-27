package com.pauldaniv.kafka.common

data class Foo2(val foo: String?) {
  override fun toString(): String {
    return "Foo2 [foo=$foo]"
  }
}

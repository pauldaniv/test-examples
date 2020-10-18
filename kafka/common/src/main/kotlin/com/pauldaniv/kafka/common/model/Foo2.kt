package com.pauldaniv.kafka.common.model

data class Foo2(val foo: String?) {
  override fun toString(): String {
    return "Foo2 [foo=$foo]"
  }
}

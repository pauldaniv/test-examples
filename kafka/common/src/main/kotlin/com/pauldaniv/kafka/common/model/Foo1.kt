package com.pauldaniv.kafka.common.model

data class Foo1(var name: String?, val foo: String?) {
  override fun toString(): String {
    return "Foo1 [name=$name, foo=$foo]"
  }
}

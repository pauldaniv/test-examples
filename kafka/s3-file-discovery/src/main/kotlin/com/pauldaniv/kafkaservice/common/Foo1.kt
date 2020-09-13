package com.pauldaniv.kafkaservice.common

data class Foo1(val foo: String?) {
  override fun toString(): String {
    return "Foo1 [foo=$foo]"
  }
}

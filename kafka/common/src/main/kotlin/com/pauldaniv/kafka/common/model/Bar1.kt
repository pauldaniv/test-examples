package com.pauldaniv.kafka.common.model

data class Bar1(val key: String?, val bar: String) {
  override fun toString(): String {
    return "Bar1 [key=$key, bar=$bar]"
  }
}

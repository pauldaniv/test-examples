package com.pauldaniv.kafka.common.model

data class Bar(val key: String, val bar: String) {
  override fun toString(): String {
    return "Bar [key=$key, bar=$bar]"
  }
}

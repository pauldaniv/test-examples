package com.pauldaniv.kafka.multymethod

import com.pauldaniv.kafka.common.model.Bar2
import com.pauldaniv.kafka.common.model.Foo2
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
@KafkaListener(id = "multiGroup", topics = ["foos", "bars"])
class MultiMethods {
  @KafkaHandler
  fun foo(foo: Foo2) {
    println("Received: $foo")
  }

  @KafkaHandler
  fun bar(bar: Bar2) {
    println("Received: $bar")
  }

  @KafkaHandler(isDefault = true)
  fun unknown(`object`: Any) {
    println("Received unknown: $`object`")
  }
}

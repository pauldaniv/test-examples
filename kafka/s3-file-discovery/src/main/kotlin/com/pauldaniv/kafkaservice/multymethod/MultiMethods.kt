package com.pauldaniv.kafkaservice.multymethod

import com.pauldaniv.kafkaservice.common.Bar2
import com.pauldaniv.kafkaservice.common.Foo2
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

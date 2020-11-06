package com.pauldaniv.kafka.discovery.tx.listeners

import com.pauldaniv.kafka.common.model.Bar
import com.pauldaniv.kafka.common.model.Foo
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

// this might be a convenient way of handling broken  messages, well, we'll see
//@Component
@KafkaListener(id = "errorHandlingGroup111", topics = ["errorHandleTopic"])
class ErrorListenerPerClass {

  // seems there is not much we can do
  // generics wouldn't work here :`(
  @KafkaHandler
  fun list(foo: List<*>) {
    println("Received: $foo")
  }

  // but just classes works fine :)
  @KafkaHandler
  fun bar(bars: Bar) {
    println("Received: $bars")
  }

  @KafkaHandler
  fun foo(bars: Foo) {
    println("Received: $bars")
  }

  // and the default one, who know what we'll get here
  @KafkaHandler(isDefault = true)
  fun unknown(`object`: Any) {
    println("Received unknown: $`object`")
  }
}

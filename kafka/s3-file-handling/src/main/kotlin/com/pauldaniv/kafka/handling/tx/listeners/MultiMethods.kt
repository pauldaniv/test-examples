package com.pauldaniv.kafka.handling.tx.listeners

import com.pauldaniv.kafka.common.model.Bar
import com.pauldaniv.kafka.common.model.Foo
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

// So apparently this type of listeners are handy when
// you dealing with a queue that you expect to receive
// different object types BUT, they should not come as
// lists because this doesn't work well with generics.
// Damn, I wish I knew this two days ago, lol -_--
@Component
@KafkaListener(id = "multiGroup", topics = ["events"])
class MultiMethods {

  @KafkaHandler
  fun foo(foo: Foo) {
    println("Received: $foo")
  }

  @KafkaHandler
  fun bar(bars: Bar) {
    println("Received: $bars")
  }

  @KafkaHandler(isDefault = true)
  fun unknown(`object`: Any) {
    println("Received unknown: $`object`")
  }
}

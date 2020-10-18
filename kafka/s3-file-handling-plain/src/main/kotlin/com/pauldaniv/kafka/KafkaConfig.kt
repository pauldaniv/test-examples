package com.pauldaniv.kafka

import com.pauldaniv.kafka.common.model.Foo1
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.KafkaListener

@Configuration
class KafkaConfig {

  private val log = LoggerFactory.getLogger(this::class.java)

  @KafkaListener(id = "default", topics = ["primary"])
  fun primaryListen(`in`: String) {
    println("Received message: $`in`")
  }

  @KafkaListener(id = "fooGroup", groupId = "plainGroup", topics = ["topic1"])
  fun listen(foo1: Foo1) {
    log.info("Received: $foo1")
    if ((foo1.foo?:"").startsWith("fail")) {
      throw RuntimeException("failed")
    }
  }
}

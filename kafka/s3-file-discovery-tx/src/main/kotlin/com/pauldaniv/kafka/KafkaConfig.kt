package com.pauldaniv.kafka

import com.pauldaniv.kafka.common.model.Foo1
import com.pauldaniv.kafka.common.model.Foo2
import org.apache.kafka.clients.admin.NewTopic
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaConfig {

  private val log = LoggerFactory.getLogger(this::class.java)

  @KafkaListener(id = "default", topics = ["primary"])
  fun primaryListen(`in`: String) {
    println("Received message: $`in`")
  }

  @KafkaListener(id = "foosGroup", topics = ["topic1"])
  fun listen(foo: Foo1) {
    log.info("Received: $foo")
    if ((foo.foo ?: "").startsWith("fail")) {
      throw RuntimeException("failed")
    }
  }

  @KafkaListener(id = "foos2Group", topics = ["topic1"])
  fun listen2(foo: Foo2) {
    log.info("Received: $foo")
    if ((foo.foo ?: "").startsWith("fail")) {
      throw RuntimeException("failed")
    }
  }

  @KafkaListener(id = "foo1Group", topics = ["topic2"])
  fun listenTopic2(foos: List<Foo1>) {
    foos.forEach {
      log.info("Received: $it")
    }
  }

  @Bean
  fun topic(): NewTopic {
    return NewTopic("topic1", 1, 1.toShort())
  }

  @Bean
  fun topic2(): NewTopic {
    return TopicBuilder.name("topic2").partitions(1).replicas(1).build()
  }

  @Bean
  fun topic3(): NewTopic {
    return TopicBuilder.name("topic3").partitions(1).replicas(1).build()
  }

  @Bean
  fun foos(): NewTopic {
    return NewTopic("foos", 1, 1.toShort())
  }

  @Bean
  fun bars(): NewTopic {
    return NewTopic("bars", 1, 1.toShort())
  }

  @Bean
  fun defaultTopic(): NewTopic {
    return NewTopic("primary", 1, 1.toShort())
  }
}

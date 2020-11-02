package com.pauldaniv.kafka.discovery.tx.listeners


import com.pauldaniv.kafka.common.model.Foo1
import com.pauldaniv.kafka.common.model.Foo2
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class CommonListeners {

  private val log = LoggerFactory.getLogger(this::class.java)

  @KafkaListener(groupId = "discovery", topics = ["primary"])
  fun primaryListen(`in`: String) {
    println("Received message: $`in`")
  }

  @KafkaListener(groupId = "discovery", topics = ["topic1"])
  fun listen(foo: Foo1) {
    log.info("Received: $foo")
    if ((foo.foo ?: "").startsWith("fail")) {
      throw RuntimeException("failed")
    }
  }

  @KafkaListener(groupId = "discovery", topics = ["topic1"])
  fun listen2(foo: Foo2) {
    log.info("Received: $foo")
    if ((foo.foo ?: "").startsWith("fail")) {
      throw RuntimeException("failed")
    }
  }

  @KafkaListener(groupId = "discovery", topics = ["topic2"])
  fun listenTopic2(foos: List<Foo1>) {
    foos.forEach {
      log.info("Received: $it")
    }
  }

  @KafkaListener(groupId = "discovery", topics = ["topic2"])
  fun listen1(foos: List<Foo1>) {
    log.info("Received: $foos")
//    foos.forEach { f: Foo1 -> template.send("topic3", f.foo) }
    log.info("Messages sent, hit Enter to commit tx")
//    System.`in`.read()
    println()
  }

  @KafkaListener(groupId = "discovery", topics = ["topic3"])
  fun listen2(`in`: List<String?>) {
    log.info("Received: $`in`")
  }

  @KafkaListener(id = "dltGroup", topics = ["topic1.DLT"])
  fun dltListen(`in`: String) {
    log.info("Received from DLT: $`in`")
  }
}

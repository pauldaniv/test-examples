package com.pauldaniv.kafka.handling.tx.listeners

import com.pauldaniv.kafka.common.model.Foo1
import com.pauldaniv.kafka.handling.tx.service.S3ObjectInfoService
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class CommonHandler(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val s3ObjectInfoService: S3ObjectInfoService,
) {

  private val log = LoggerFactory.getLogger(this::class.java)

  @KafkaListener(groupId = "handling", topics = ["topic2"])
  fun listen1(foos: List<Foo1>) {
    log.info("Received: $foos")
    kafkaTemplate.send("received", "received")
    s3ObjectInfoService.storeObjects(foos.map { it.foo })
    log.info("Persisted: $foos")

    kafkaTemplate.send("persisted", "persisted")
    foos.forEach { f: Foo1 -> kafkaTemplate.send("topic3", f.foo) }
    kafkaTemplate.send("transferred", "transferred")
    log.info("Messages sent, hit Enter to commit tx")

    //fixme might be a good idea to use replying template to ensure message was delivered or smth
//    System.`in`.read()
    println()
  }

  @KafkaListener(groupId = "handling", topics = ["topic3"])
  fun listen2(`in`: List<String?>) {
    log.info("Received: $`in`")
  }

  @KafkaListener(groupId = "handling", topics = ["topic1"])
  fun listen(foo1: Foo1) {
    log.info("Received: $foo1")
    if ((foo1.foo ?: "").startsWith("fail")) {
      throw RuntimeException("failed")
    }
  }

  @KafkaListener(id = "dltGroup", topics = ["topic1.DLT"])
  fun dltListen(`in`: String) {
    log.info("Received from DLT: $`in`")
  }
}

package com.pauldaniv.kafka.discovery.listeners

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class ErrorListenerCommon {
  private val log = LoggerFactory.getLogger(this::class.java)

  // try protobuf
  // I guess I'll need to add ErrorMessageDeserializer or something
  @KafkaListener(groupId = "errorHandlingGroup222", topics = ["errorHandleTopic"])
  fun listen1(stuff: String) {
    log.info("Received: $stuff")
  }
}

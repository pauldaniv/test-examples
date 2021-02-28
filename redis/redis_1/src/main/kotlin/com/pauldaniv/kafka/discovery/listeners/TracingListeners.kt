package com.pauldaniv.kafka.discovery.listeners

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class TracingListeners {

  private val log = LoggerFactory.getLogger(this::class.java)

  @KafkaListener(groupId = "dltGroup", topics = ["topic2.DLT"])
  fun dltListen(troubleData: String) {
    log.info("Received from DLT: $troubleData")
  }
}

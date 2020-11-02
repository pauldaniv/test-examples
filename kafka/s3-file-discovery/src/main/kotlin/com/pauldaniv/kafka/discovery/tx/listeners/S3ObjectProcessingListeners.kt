package com.pauldaniv.kafka.discovery.tx.listeners

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class S3ObjectProcessingListeners {

  private val log = LoggerFactory.getLogger(this::class.java)

  @KafkaListener(id = "s3-receive", groupId = "s3-processing", topics = ["received"])
  fun received(res: String) {
    log.info("Received: $res")
  }

  @KafkaListener(id = "s3-persist", groupId = "s3-processing", topics = ["persisted"])
  fun persisted(res: String) {
    log.info("Persisted: $res")
  }

  @KafkaListener(id = "s3-transfer", groupId = "s3-processing", topics = ["transferred"])
  fun transferred(res: String) {
    log.info("Transferred: $res")
  }
}

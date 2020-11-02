package com.pauldaniv.kafka.handling.tx.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
class S3ObjectInfoService {
  private val log = LoggerFactory.getLogger(this::class.java)

  @Transactional("chainedKafkaTransactionManager")
  fun storeObjects(s3Objects: List<String?>) {
    log.info("Persisting {}", s3Objects)
    log.info("Persisted!")
  }
}

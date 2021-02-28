package com.pauldaniv.kafka.handling.service

import com.pauldaniv.kafka.common.model.Foo
import com.pauldaniv.kafka.handling.repository.FooRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class S3ObjectInfoService(
    private val fooRepository: FooRepository
) {
  private val log = LoggerFactory.getLogger(this::class.java)

  @Transactional("chainedKafkaTransactionManager")
  fun storeObjects(s3Objects: List<Foo>) {
    log.info("Persisting {}", s3Objects)
    fooRepository.saveAll(s3Objects)
    log.info("Persisted!")
  }
}

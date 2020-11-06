package com.pauldaniv.kafka.discovery.tx.service

import com.pauldaniv.kafka.common.model.Bar
import com.pauldaniv.kafka.common.model.Foo
import org.springframework.kafka.core.KafkaOperations
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

@Component
class S3ObjectProducerService(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
) {

  fun sendFoos(what: String?) {
    kafkaTemplate.executeInTransaction<Any?> { kafkaTemplate: KafkaOperations<String, Any> ->
      StringUtils.commaDelimitedListToSet(what).stream()
          .map { Bar("key", it) }
          .forEach { kafkaTemplate.send("topic2", it) }
      Foo("key", "done")
    }
  }

  fun sendS3Objects(objectKeys: List<Bar>) {
    kafkaTemplate.executeInTransaction { kafkaTemplate: KafkaOperations<String, Any> ->
      objectKeys.forEach {
        kafkaTemplate.send("topic2", it)
      }
    }
  }
}

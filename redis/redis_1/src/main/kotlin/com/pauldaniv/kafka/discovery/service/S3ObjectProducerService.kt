package com.pauldaniv.kafka.discovery.service

import com.pauldaniv.kafka.common.model.Bar
import org.springframework.kafka.core.KafkaOperations
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils

@Service
class S3ObjectProducerService(
    private val kafkaTemplate: KafkaTemplate<String, Any>,
) : FileProducerService {

  override fun sendFoos(what: String?) {
    sendInTransaction(StringUtils.commaDelimitedListToSet(what).map { Bar("key", it) })
  }

  override fun sendS3Objects(objectKeys: List<Bar>) {
    sendInTransaction(objectKeys)
  }

  private fun sendInTransaction(objects: List<Any>) {
    kafkaTemplate.executeInTransaction { kafkaTemplate: KafkaOperations<String, Any> ->
      objects.forEach {
        kafkaTemplate.send("topic2", it)
      }
    }
  }
}

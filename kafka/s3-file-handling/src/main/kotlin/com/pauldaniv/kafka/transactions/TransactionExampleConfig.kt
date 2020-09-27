package com.pauldaniv.kafka.transactions

import com.pauldaniv.kafka.common.Foo1
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter
import org.springframework.kafka.support.converter.RecordMessageConverter

@Configuration
class TransactionExampleConfig(private val template: KafkaTemplate<Any?, Any?>) {

  private val log = LoggerFactory.getLogger(TransactionExampleConfig::class.java)

  @Bean
  fun batchConverter(converter: RecordMessageConverter): BatchMessagingMessageConverter {
    return BatchMessagingMessageConverter(converter)
  }

  @KafkaListener(id = "fooGroup2", topics = ["topic2"])
  fun listen1(foos: List<Foo1>) {
    log.info("Received: $foos")
    foos.forEach { f: Foo1 -> template.send("topic3", f.foo?.toUpperCase()) }
    log.info("Messages sent, hit Enter to commit tx")
    System.`in`.read()
    println()
  }

  @KafkaListener(id = "fooGroup3", topics = ["topic3"])
  fun listen2(`in`: List<String?>) {
    log.info("Received: $`in`")
  }
}

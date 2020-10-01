package com.pauldaniv.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.pauldaniv.kafka.common.Bar2
import com.pauldaniv.kafka.common.Foo1
import com.pauldaniv.kafka.common.Foo2
import org.apache.kafka.clients.admin.NewTopic
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaOperations
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.SeekToCurrentErrorHandler
import org.springframework.kafka.support.converter.*
import org.springframework.util.backoff.FixedBackOff
import java.io.Serializable
import java.util.*

@Configuration
class KafkaConfig : Serializable {

  private val log = LoggerFactory.getLogger(KafkaConfig::class.java)

  @Autowired
  fun objectMapper(objectMapper: ObjectMapper) {
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
  }

  @Bean
  fun converter(): RecordMessageConverter {
    val converter = StringJsonMessageConverter()
    val typeMapper = DefaultJackson2JavaTypeMapper()
    typeMapper.typePrecedence = Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID
    typeMapper.addTrustedPackages("com.pauldaniv.kafka.common")
    val mappings: MutableMap<String, Class<*>> = HashMap()
    mappings["foo"] = Foo2::class.java
    mappings["foo1"] = Foo1::class.java
    mappings["bar"] = Bar2::class.java
    typeMapper.idClassMapping = mappings
    converter.typeMapper = typeMapper
    return converter
  }

  @KafkaListener(id = "default", topics = ["primary"])
  fun primaryListen(`in`: String) {
    println("Received message: $`in`")
  }

  @KafkaListener(id = "foosGroup", topics = ["topic1"])
  fun listen(foo: Foo1) {
    log.info("Received: $foo")
    if ((foo.foo?:"").startsWith("fail")) {
      throw RuntimeException("failed")
    }
  }

  @KafkaListener(id = "foos2Group", topics = ["topic1"])
  fun listen2(foo: Foo2) {
    log.info("Received: $foo")
    if ((foo.foo?:"").startsWith("fail")) {
      throw RuntimeException("failed")
    }
  }

  @KafkaListener(id = "foo1Group", topics = ["topic2"])
  fun listenTopic2(foos: List<Foo1>) {
    foos.forEach {
      log.info("Received: $it")
    }
  }

  @KafkaListener(id = "dltGroup", topics = ["topic1.DLT"])
  fun dltListen(`in`: String) {
    log.info("Received from DLT: $`in`")
  }

  @Bean
  fun topic(): NewTopic {
    return NewTopic("topic1", 1, 1.toShort())
  }

  @Bean
  fun topic2(): NewTopic {
    return TopicBuilder.name("topic2").partitions(1).replicas(1).build()
  }

  @Bean
  fun topic3(): NewTopic {
    return TopicBuilder.name("topic3").partitions(1).replicas(1).build()
  }

  @Bean
  fun errorHandler(template: KafkaTemplate<*, *>): SeekToCurrentErrorHandler {
    return SeekToCurrentErrorHandler(
        DeadLetterPublishingRecoverer(template as KafkaOperations<out Any, out Any>), FixedBackOff(1000L, 2))
  }

  @Bean
  fun foos(): NewTopic {
    return NewTopic("foos", 1, 1.toShort())
  }

  @Bean
  fun bars(): NewTopic {
    return NewTopic("bars", 1, 1.toShort())
  }

  @Bean
  fun defaultTopic(): NewTopic {
    return NewTopic("primary", 1, 1.toShort())
  }

  @Bean
  fun dlt(): NewTopic {
    return NewTopic("topic1.DLT", 1, 1.toShort())
  }
}

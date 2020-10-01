package com.pauldaniv.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.pauldaniv.kafka.common.Bar2
import com.pauldaniv.kafka.common.Foo2
import com.pauldaniv.kafka.common.Foo1
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.KafkaListener
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
//
//  @Value("\${spring.kafka.template.default-topic}")
//  private val defaultTopic: String? = null

  @Bean
  fun converter(): RecordMessageConverter {
    val converter = StringJsonMessageConverter()
    val typeMapper = DefaultJackson2JavaTypeMapper()
    typeMapper.typePrecedence = Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID
    typeMapper.addTrustedPackages("com.pauldaniv.kafka.common")
    val mappings: MutableMap<String, Class<*>> = HashMap()
    mappings["foo2"] = Foo2::class.java
    mappings["foo1"] = Foo1::class.java
    mappings["bar"] = Bar2::class.java
    typeMapper.idClassMapping = mappings
    converter.typeMapper = typeMapper
    return converter
  }

  @Bean
  fun errorHandler(template: KafkaTemplate<*, *>): SeekToCurrentErrorHandler {
    return SeekToCurrentErrorHandler(
        DeadLetterPublishingRecoverer(template as KafkaOperations<out Any, out Any>), FixedBackOff(1000L, 2))
  }

  @Autowired
  fun objectMapper(objectMapper: ObjectMapper) {
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
  }

  @KafkaListener(id = "default", topics = ["primary"])
  fun primaryListen(`in`: String) {
    println("Received message: $`in`")
  }

  @KafkaListener(id = "fooGroup", topics = ["topic1"])
  fun listen(foo1: Foo1) {
    log.info("Received: $foo1")
    if ((foo1.foo?:"").startsWith("fail")) {
      throw RuntimeException("failed")
    }
  }
}

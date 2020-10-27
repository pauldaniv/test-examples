package com.pauldaniv.kafka.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.pauldaniv.kafka.common.model.Foo1
import org.apache.kafka.clients.admin.NewTopic
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.converter.DefaultJackson2JavaTypeMapper
import org.springframework.kafka.support.converter.Jackson2JavaTypeMapper
import org.springframework.kafka.support.converter.RecordMessageConverter
import org.springframework.kafka.support.converter.StringJsonMessageConverter

@Configuration
class CommonKafkaConfig {

  private val log = LoggerFactory.getLogger(this::class.java)

  @Primary
  @Bean
  fun replyTemplate(pf: ProducerFactory<String, Foo1>,
                    factory: ConcurrentKafkaListenerContainerFactory<String, Foo1>): KafkaTemplate<String, Foo1> {
    val kafkaTemplate = KafkaTemplate(pf)
    factory.containerProperties.isMissingTopicsFatal = false
    factory.setReplyTemplate(kafkaTemplate)
    return kafkaTemplate
  }

  @Bean
  fun converter(): RecordMessageConverter {
    val converter = StringJsonMessageConverter()
    val typeMapper = DefaultJackson2JavaTypeMapper()
    typeMapper.addTrustedPackages("com.pauldaniv.kafka.common.model")
    typeMapper.typePrecedence = Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID
    converter.typeMapper = typeMapper
    return converter
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
    if ((foo1.foo ?: "").startsWith("fail")) {
      throw RuntimeException("failed")
    }
  }

//fixme this breaks the build, keeping this just in case
//  @Autowired
//  @Bean
//  fun errorHandler(template: KafkaTemplate<*, *>): SeekToCurrentErrorHandler {
//    return SeekToCurrentErrorHandler(
//        DeadLetterPublishingRecoverer(template as KafkaOperations<out Any, out Any>), FixedBackOff(1000L, 2))
//  }

  @KafkaListener(id = "dltGroup", topics = ["topic1.DLT"])
  fun dltListen(`in`: String) {
    log.info("Received from DLT: $`in`")
  }

  @Bean
  fun dlt(): NewTopic {
    return NewTopic("topic1.DLT", 1, 1.toShort())
  }
}

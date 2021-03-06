package com.pauldaniv.kafka.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.converter.*

@Configuration
class CommonKafkaConfig {

  @Bean
  fun kafkaTemplate(kafkaProducerFactory: ProducerFactory<String, *>,
                    messageConverter: ObjectProvider<RecordMessageConverter>,
                    context: ApplicationContext): KafkaTemplate<String, *> {
    val kafkaTemplate = KafkaTemplate(kafkaProducerFactory)
    messageConverter.ifUnique { kafkaTemplate.setMessageConverter(it) }
    kafkaTemplate.defaultTopic = "default"
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

  @ConditionalOnProperty(value = ["spring.kafka.listener.type"], havingValue = "batch")
  @Bean
  fun batchConverter(converter: RecordMessageConverter): BatchMessagingMessageConverter {
    return BatchMessagingMessageConverter(converter)
  }

  @Autowired
  fun objectMapper(objectMapper: ObjectMapper) {
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
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
  fun received(): NewTopic {
    return TopicBuilder.name("received").partitions(1).replicas(1).build()
  }

  @Bean
  fun persisted(): NewTopic {
    return TopicBuilder.name("persisted").partitions(1).replicas(1).build()
  }

  @Bean
  fun transferred(): NewTopic {
    return TopicBuilder.name("transferred").partitions(1).replicas(1).build()
  }

  @Bean
  fun default(): NewTopic {
    return TopicBuilder.name("default").partitions(1).replicas(1).build()
  }

  // it's error handling time!
  @Bean
  fun errorHandelTopic(): NewTopic {
    return TopicBuilder.name("errorHandleTopic").partitions(1).replicas(1).build()
  }

  @Bean
  fun dlt(): NewTopic {
    return TopicBuilder.name("topic2.DLT").partitions(1).replicas(1).build()
  }

// if we declare an errorHandler, it will make the transaction to commit with bad message in it, sad but true
//  @Bean
//  fun errorHandler(template: KafkaOperations<Any, Any>): SeekToCurrentErrorHandler? {
//    return SeekToCurrentErrorHandler(
//        DeadLetterPublishingRecoverer(template), FixedBackOff(1000L, 2))
//  }
}

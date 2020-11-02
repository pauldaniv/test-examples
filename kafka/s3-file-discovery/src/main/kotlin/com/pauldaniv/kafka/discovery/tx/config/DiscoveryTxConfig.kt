package com.pauldaniv.kafka.discovery.tx.config

import com.pauldaniv.kafka.common.config.CommonKafkaConfig
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.listener.SeekToCurrentBatchErrorHandler
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter
import org.springframework.kafka.support.converter.RecordMessageConverter

@Configuration
@Import(CommonKafkaConfig::class)
class DiscoveryTxConfig {

//  @Bean
//  fun kafkaListenerContainerFactory(
//      configurer: ConcurrentKafkaListenerContainerFactoryConfigurer,
//      kafkaConsumerFactory: ConsumerFactory<Any, Any>): ConcurrentKafkaListenerContainerFactory<*, *> {
//    val factory = ConcurrentKafkaListenerContainerFactory<Any, Any>()
//    configurer.configure(factory, kafkaConsumerFactory)
//    factory.setBatchErrorHandler(SeekToCurrentBatchErrorHandler())
//    return factory
//  }
}

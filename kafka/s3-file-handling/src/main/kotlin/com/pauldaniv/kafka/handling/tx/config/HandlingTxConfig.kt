package com.pauldaniv.kafka.handling.tx.config

import com.pauldaniv.kafka.common.config.CommonKafkaConfig
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.transaction.ChainedKafkaTransactionManager
import org.springframework.kafka.transaction.KafkaTransactionManager
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.AbstractPlatformTransactionManager.SYNCHRONIZATION_ON_ACTUAL_TRANSACTION


@Configuration
@Import(CommonKafkaConfig::class)
class HandlingTxConfig {

//  @Bean
//  fun transactionManager(entityManagerFactory: EntityManagerFactory?): JpaTransactionManager? {
//    return JpaTransactionManager(entityManagerFactory)
//  }

  @Bean
  fun transactionManager(): JpaTransactionManager? {
    return JpaTransactionManager()
  }

  @Bean
  fun chainedKafkaTransactionManager(
      kafkaTransactionManager: KafkaTransactionManager<*, *>,
      transactionManager: PlatformTransactionManager): ChainedKafkaTransactionManager<Any, Any> {
    kafkaTransactionManager.transactionSynchronization = SYNCHRONIZATION_ON_ACTUAL_TRANSACTION;
    return ChainedKafkaTransactionManager(kafkaTransactionManager, transactionManager)
  }

  @Bean
  fun kafkaListenerContainerFactory(
      configurer: ConcurrentKafkaListenerContainerFactoryConfigurer,
      consumerFactory: ConsumerFactory<Any, Any>,
      chainedKafkaTransactionManager: ChainedKafkaTransactionManager<*, *>): ConcurrentKafkaListenerContainerFactory<*, *>? {
    val factory = ConcurrentKafkaListenerContainerFactory<Any, Any>()
    configurer.configure(factory, consumerFactory)
    factory.containerProperties.transactionManager = chainedKafkaTransactionManager
    return factory
  }

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

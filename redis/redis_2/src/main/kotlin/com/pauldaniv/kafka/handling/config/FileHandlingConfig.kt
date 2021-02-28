package com.pauldaniv.kafka.handling.config

import com.pauldaniv.kafka.common.config.CommonKafkaConfig
import org.apache.kafka.common.errors.TimeoutException
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.KafkaOperations
import org.springframework.kafka.transaction.ChainedKafkaTransactionManager
import org.springframework.kafka.transaction.KafkaTransactionManager
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.AbstractPlatformTransactionManager.SYNCHRONIZATION_ON_ACTUAL_TRANSACTION
import java.io.IOException

@Configuration
@Import(CommonKafkaConfig::class)
class FileHandlingConfig {
  private val log = LoggerFactory.getLogger(this::class.java)

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
      chainedKafkaTransactionManager: ChainedKafkaTransactionManager<*, *>,
      template: KafkaOperations<String, Any>,
  ): ConcurrentKafkaListenerContainerFactory<*, *>? {
    val factory = ConcurrentKafkaListenerContainerFactory<Any, Any>()
    configurer.configure(factory, consumerFactory)
    factory.containerProperties.transactionManager = chainedKafkaTransactionManager
//    factory.containerProperties.ackMode = ContainerProperties.AckMode.RECORD
//    factory.containerProperties.isAckOnError = true
    // here we just tell spring to retry the message if we hit an error, if that didn't help,
    // send the data to DLT topic
    // But, after that, this crap would 'read' and commit the offset so the broken transaction will happen
    // Thus, something more cleaver needed if we want to skip the broken messages from being sent, put it into separated
    // topic and then 'hopefully' review and see what da heck is going on. Then just drop it or try to proceed again.
    // But again, if the retries didn't help, that could mean there is something weird going on. Let's say the file
    // in S3 bucket (the only scenario is it was removed right after discovered in S3) and can no longer be accessed
    // But that's kinda obvious, if file doesn't exists just leave it as it is
    // Anyways, I have to wrap my head around it someday :)
    factory.setRetryTemplate(retryTemplate())

//    factory.setErrorHandler { e, data ->
//      template.send("errorHandleTopic", "data.value() oh shu, an error happened")
      // ideally we should be able to send error message to another topic and re-throw the error here
//    }
    return factory
  }

  private fun retryTemplate(): RetryTemplate {
    val retryTemplate = RetryTemplate()
    retryTemplate.setRetryPolicy(getSimpleRetryPolicy())
    return retryTemplate
  }

  private fun getSimpleRetryPolicy(): SimpleRetryPolicy {
    val exceptionMap: MutableMap<Class<out Throwable?>, Boolean> = HashMap()
    // the boolean value in the map determines whether exception should be retried
    exceptionMap[IllegalArgumentException::class.java] = false
    exceptionMap[IllegalStateException::class.java] = false
    exceptionMap[TimeoutException::class.java] = true
    exceptionMap[IOException::class.java] = true
    return SimpleRetryPolicy(3, exceptionMap, true)
  }
}

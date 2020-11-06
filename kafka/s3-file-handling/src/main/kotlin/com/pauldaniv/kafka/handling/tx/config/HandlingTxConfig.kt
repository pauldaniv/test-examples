package com.pauldaniv.kafka.handling.tx.config

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
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer
import org.springframework.kafka.listener.RetryingBatchErrorHandler
import org.springframework.kafka.transaction.ChainedKafkaTransactionManager
import org.springframework.kafka.transaction.KafkaTransactionManager
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.retry.policy.SimpleRetryPolicy
import org.springframework.retry.support.RetryTemplate
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.AbstractPlatformTransactionManager.SYNCHRONIZATION_ON_ACTUAL_TRANSACTION
import org.springframework.util.backoff.FixedBackOff

@Configuration
@Import(CommonKafkaConfig::class)
class HandlingTxConfig {
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

    // here we just tell spring to retry the message if we hit an error, if that didn't help,
    // send the data to DLT topic
    // But, after that, this crap would 'read' and commit the offset so the broken transaction will happen
    // Thus, something bore cleaver needed if we want to skip the broken messages from being sent, put it into separated
    // topic and then 'hopefully' review and see what da heck is going on. Then just drop it or try to proceed again.
    // But again, if the retries didn't help, that could mean there is something weird going on. Let's say the file
    // in S3 bucket (the only scenario is it was removed right after discovered in S3) and can no longer be accessed
    // But that's kinda obvious, if file doesn't exists just leave it as it is
    // Anyways, I have to wrap my head around it someday :)
    factory.setBatchErrorHandler(RetryingBatchErrorHandler(FixedBackOff(1000, 3), DeadLetterPublishingRecoverer(template)))

// doesn't work with transactions :(
// keep it here just for reference

//    factory.setRetryTemplate(retryTemplate())
//    factory.setRecoveryCallback { context: RetryContext ->
//      if (context.lastThrowable.cause is RecoverableDataAccessException) {
//
//        //here you can do your recovery mechanism where you can put back on to the topic using a Kafka producer
//        log.info("Recoverable !")
//      } else {
//
//        template.send("commonErrorHandling", )
//        // here you can log things and throw some custom exception that Error handler will take care of ..
//        log.info("Not recoverable!")
//        throw RuntimeException(context.lastThrowable.message)
//      }
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
    exceptionMap[IllegalArgumentException::class.java] = true
    exceptionMap[IllegalStateException::class.java] = true
    exceptionMap[TimeoutException::class.java] = true
    return SimpleRetryPolicy(3, exceptionMap, true)
  }
}

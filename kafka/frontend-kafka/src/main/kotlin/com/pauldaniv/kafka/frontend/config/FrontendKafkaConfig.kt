package com.pauldaniv.kafka.frontend.config

import com.pauldaniv.kafka.common.config.CommonKafkaConfig
import com.pauldaniv.kafka.common.model.Bar1
import com.pauldaniv.kafka.common.model.Foo1
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.kafka.support.ProducerListener
import org.springframework.kafka.support.converter.RecordMessageConverter
import org.springframework.messaging.handler.annotation.SendTo
import java.util.concurrent.ThreadLocalRandom

@Configuration
@Import(CommonKafkaConfig::class)
class FrontendKafkaConfig {

  @Value("\${kafka.group.id}")
  private val groupId: String? = null

  @Value("\${kafka.reply.topic}")
  private val replyTopic: String? = null

  @Bean
  fun replyingKafkaTemplate(pf: ProducerFactory<String, Foo1>,
                            factory: ConcurrentKafkaListenerContainerFactory<String, Bar1?>)
      : ReplyingKafkaTemplate<String, Foo1, Bar1?> {
    val replyContainer = factory.createContainer(replyTopic)
    replyContainer.containerProperties.isMissingTopicsFatal = false
    replyContainer.containerProperties.groupId = groupId
    return ReplyingKafkaTemplate(pf, replyContainer)
  }


//  @Bean
//  fun errorHandler(kafkaTemplate: KafkaOperations<*, *>): SeekToCurrentErrorHandler {
//    return SeekToCurrentErrorHandler(
//        DeadLetterPublishingRecoverer(kafkaTemplate), FixedBackOff(1000L, 2))
//  }
}

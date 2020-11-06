package com.pauldaniv.kafka.frontend.config

import com.pauldaniv.kafka.common.config.CommonKafkaConfig
import com.pauldaniv.kafka.common.model.Bar
import com.pauldaniv.kafka.common.model.Foo
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate

@Configuration
@Import(CommonKafkaConfig::class)
class FrontendKafkaConfig {

  @Value("\${kafka.group.id}")
  private val groupId: String? = null

  @Value("\${kafka.reply.topic}")
  private val replyTopic: String? = null

  @Bean
  fun replyingKafkaTemplate(pf: ProducerFactory<String, Foo>,
                            factory: ConcurrentKafkaListenerContainerFactory<String, Bar?>)
      : ReplyingKafkaTemplate<String, Foo, Bar?> {
    val replyContainer = factory.createContainer(replyTopic)
    replyContainer.containerProperties.isMissingTopicsFatal = false
    replyContainer.containerProperties.groupId = groupId
    return ReplyingKafkaTemplate(pf, replyContainer)
  }
}

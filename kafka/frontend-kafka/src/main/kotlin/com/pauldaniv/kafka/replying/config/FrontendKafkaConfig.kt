package com.pauldaniv.kafka.replying.config

import com.pauldaniv.kafka.common.config.CommonKafkaConfig
import com.pauldaniv.kafka.common.model.Bar
import com.pauldaniv.kafka.common.model.Foo
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate

@Configuration
@Import(CommonKafkaConfig::class)
class FrontendKafkaConfig {

  @Bean
  fun replyingKafkaTemplate(pf: ProducerFactory<String, Foo>,
                            factory: ConcurrentKafkaListenerContainerFactory<String, Bar?>)
      : ReplyingKafkaTemplate<String, Foo, Bar?> {
    val replyContainer = factory.createContainer("replyMe")
    replyContainer.containerProperties.isMissingTopicsFatal = false
    return ReplyingKafkaTemplate(pf, replyContainer)
  }
}

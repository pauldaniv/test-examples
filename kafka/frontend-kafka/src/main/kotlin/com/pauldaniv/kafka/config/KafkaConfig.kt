package com.pauldaniv.kafka.config

import com.pauldaniv.kafka.common.model.Bar1
import com.pauldaniv.kafka.common.model.Foo1
import org.apache.kafka.clients.admin.NewTopic
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate

@Configuration
class KafkaConfig {

  private val log = LoggerFactory.getLogger(this::class.java)

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


  @KafkaListener(id = "foo1Group", topics = ["topic2"])
  fun listenTopic2(foos: List<Foo1>) {
    foos.forEach {
      log.info("Received: $it")
    }
  }

  @Bean
  fun topic(): NewTopic {
    return NewTopic("topic1", 1, 1.toShort())
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
  fun foos(): NewTopic {
    return NewTopic("foos", 1, 1.toShort())
  }

  @Bean
  fun bars(): NewTopic {
    return NewTopic("bars", 1, 1.toShort())
  }

  @Bean
  fun defaultTopic(): NewTopic {
    return NewTopic("primary", 1, 1.toShort())
  }
}

package com.pauldaniv.kafka.frontend.handler

import com.pauldaniv.kafka.common.model.Bar
import com.pauldaniv.kafka.common.model.Foo
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Component
import java.util.concurrent.ThreadLocalRandom

@Component
class ReplyHandler {

  private val log = LoggerFactory.getLogger(this::class.java)

  @KafkaListener(topics = ["\${kafka.request.topic}"], groupId = "\${kafka.group.id}")
  @SendTo
  fun handle(bar: Foo): Bar {
    val total: Double = ThreadLocalRandom.current().nextDouble(2.5, 9.9)
    return Bar(bar.name, if (total > 3.5) "Pass" else "Fail")
  }
}

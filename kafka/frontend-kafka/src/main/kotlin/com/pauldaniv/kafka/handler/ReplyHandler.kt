package com.pauldaniv.kafka.handler

import com.pauldaniv.kafka.common.model.Bar1
import com.pauldaniv.kafka.common.model.Foo1
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Component
import java.util.concurrent.ThreadLocalRandom

@Component
class ReplyHandler {
  @KafkaListener(topics = ["\${kafka.request.topic}"], groupId = "\${kafka.group.id}")
  @SendTo
  fun handle(bar1: Foo1): Bar1 {
    val total: Double = ThreadLocalRandom.current().nextDouble(2.5, 9.9)
    return Bar1(bar1.name, if (total > 3.5) "Pass" else "Fail")
  }
}

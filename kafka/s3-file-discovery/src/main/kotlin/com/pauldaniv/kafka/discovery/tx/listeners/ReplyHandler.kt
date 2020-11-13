package com.pauldaniv.kafka.discovery.tx.listeners

import com.pauldaniv.kafka.common.model.Bar
import com.pauldaniv.kafka.common.model.Foo
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Component
import java.util.concurrent.ThreadLocalRandom

@Component
class ReplyHandler {
  @KafkaListener(id = "replyMeHandle", topics = ["requestTopic"])
  @SendTo("replyMe")
  fun handle(bar: Foo): Bar {
    val total: Double = ThreadLocalRandom.current().nextDouble(2.5, 9.9)
    return Bar(bar.name, if (total > 3.5) "Pass" else "Fail")
  }
}

package com.pauldaniv.kafka.transactions

import com.pauldaniv.kafka.common.Foo1
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.kafka.core.KafkaOperations
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Message Controller", description = "Used for pushing messages to the kafka queue")
@RestController
@RequestMapping("/tx")
class TransactionsController(private val template: KafkaTemplate<Any?, Any?>) {

  @PostMapping("/send/foos/{what}")
  fun sendFoo(@PathVariable what: String?) {
    template.executeInTransaction<Any?> { kafkaTemplate: KafkaOperations<Any?, in Any?> ->
      StringUtils.commaDelimitedListToSet(what).stream()
          .map { s: String? -> Foo1(s) }
          .forEach { foo: Foo1? -> kafkaTemplate.send("topic2", foo) }
      Foo1("done")
    }
  }
}

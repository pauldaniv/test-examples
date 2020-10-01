package com.pauldaniv.kafka.controller

import com.pauldaniv.kafka.common.Foo1
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/plain")
class PlainExampleController(private val template: KafkaTemplate<Any, Any>) {
  @PostMapping("/send/foo/{what}")
  fun sendFoo(@PathVariable what: String?) {
    template.send("topic1", Foo1(what))
  }
}

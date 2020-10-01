package com.pauldaniv.kafka.controller

import com.pauldaniv.kafka.common.Bar1
import com.pauldaniv.kafka.common.Foo1
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/multi")
class MultimethodController(private val template: KafkaTemplate<Any, Any>) {

  @PostMapping("/send/foo/{what}")
  fun sendFoo(@PathVariable what: String?) {
    template.send("foos", Foo1(what))
  }

  @PostMapping("/send/bar/{what}")
  fun sendBar(@PathVariable what: String?) {
    template.send("bars", Bar1(what))
  }

  @PostMapping("/send/unknown/{what}")
  fun sendUnknown(@PathVariable what: String) {
    template.send("bars", what)
  }
}

package com.pauldaniv.kafka.frontend.controller

import com.pauldaniv.kafka.common.model.Bar
import com.pauldaniv.kafka.common.model.Foo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "FileDiscoveryController", description = "Sends message to a queue and returns the reply")
@RestController
@RequestMapping("/fe")
class ReplyingController(private val replyingKafkaTemplate: ReplyingKafkaTemplate<String, Foo, Bar?>) {

  @Operation(summary = "Gets a single MerchantEntry object by id")
  @PostMapping("/get-result")
  fun getObject(@RequestBody bar: Foo): ResponseEntity<Bar?> {
    val future = replyingKafkaTemplate.sendAndReceive(ProducerRecord("requestTopic", null, bar.name, bar))
    val response = future.get()
    return ResponseEntity(response.value(), HttpStatus.OK)
  }
}

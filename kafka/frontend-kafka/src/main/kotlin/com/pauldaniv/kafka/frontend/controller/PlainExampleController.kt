package com.pauldaniv.kafka.frontend.controller

import com.pauldaniv.kafka.common.model.Bar1
import com.pauldaniv.kafka.common.model.Foo1

import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/plain")
class PlainExampleController(private val replyingKafkaTemplate: ReplyingKafkaTemplate<String, Foo1, Bar1?>) {

  @Value("\${kafka.request.topic}")
  private val requestTopic: String? = null

  @PostMapping("/get-result")
  fun getObject(@RequestBody bar1: Foo1): ResponseEntity<Bar1?> {
    val future = replyingKafkaTemplate.sendAndReceive(ProducerRecord(requestTopic, null, bar1.name, bar1))
    val response = future.get()
    return ResponseEntity(response.value(), HttpStatus.OK)
  }
}

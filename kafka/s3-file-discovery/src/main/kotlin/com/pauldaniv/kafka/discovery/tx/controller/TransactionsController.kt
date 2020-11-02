package com.pauldaniv.kafka.discovery.tx.controller

import com.pauldaniv.kafka.discovery.tx.service.S3ObjectProcessingService
import com.pauldaniv.kafka.discovery.tx.service.S3ObjectProducerService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Message Controller", description = "Used for pushing messages to the kafka queue")
@RestController
@RequestMapping("/tx")
class TransactionsController(
  private val s3ObjectProducerService: S3ObjectProducerService,
  private val s3ObjectProcessingService: S3ObjectProcessingService,
) {

  @PostMapping("/send/foos/{what}")
  fun sendFoo(@PathVariable what: String?) {
    s3ObjectProducerService.sendFoos(what)
  }

  @PostMapping("/s3-discovery/{bucket}")
  fun listS3(@PathVariable bucket: String) {
    s3ObjectProcessingService.processS3Objets(bucket)
  }
}

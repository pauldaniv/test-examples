package com.pauldaniv.kafka.discovery.controller

import com.pauldaniv.kafka.discovery.service.FileProducerService
import com.pauldaniv.kafka.discovery.service.S3ObjectProcessingService
import com.pauldaniv.kafka.discovery.service.S3ObjectProducerService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "FileDiscoveryController", description = "Discovers objects in S3 and sends messages to a kafka queue")
@RestController
@RequestMapping("/discovery")
class FileDiscoveryController(
    private val fileProducerService: FileProducerService,
    private val fileProcessingService: S3ObjectProcessingService,
) {

  @Operation(summary = "Process given CSV string and pushes messages to a queue")
  @PostMapping("/send/foos/{what}")
  fun sendFoo(@PathVariable what: String?) {
    fileProducerService.sendFoos(what)
  }

  @Operation(summary = "Discovers files in given bucket, and pushes file keys to a kafka queue")
  @PostMapping("/file-discovery/{bucket}/{custom}")
  fun listS3(@PathVariable bucket: String, @PathVariable custom: String?) {
    fileProcessingService.processFiles(bucket, custom)
  }
}

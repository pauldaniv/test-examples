package com.pauldaniv.kafka.frontend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FrontendKafkaApplication

fun main(args: Array<String>) {
  runApplication<FrontendKafkaApplication>(*args)
}

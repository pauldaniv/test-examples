package com.pauldaniv.kafka.replying

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReplyingKafkaApplication

fun main(args: Array<String>) {
  runApplication<ReplyingKafkaApplication>(*args)
}

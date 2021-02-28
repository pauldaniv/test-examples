package com.pauldaniv.kafka.handling

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@EntityScan("com.pauldaniv.kafka.common.model")
@SpringBootApplication
@EnableTransactionManagement
class KafkaFileHandlingApplication

fun main(args: Array<String>) {
  runApplication<KafkaFileHandlingApplication>(*args)
}

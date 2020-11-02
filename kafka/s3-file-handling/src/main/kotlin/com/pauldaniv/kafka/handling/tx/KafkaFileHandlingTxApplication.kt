package com.pauldaniv.kafka.handling.tx

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableTransactionManagement
class KafkaFileHandlingTxApplication

fun main(args: Array<String>) {
	runApplication<KafkaFileHandlingTxApplication>(*args)
}

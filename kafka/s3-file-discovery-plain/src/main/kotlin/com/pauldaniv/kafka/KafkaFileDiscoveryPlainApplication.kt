package com.pauldaniv.kafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaFileDiscoveryPlainApplication

fun main(args: Array<String>) {
	runApplication<KafkaFileDiscoveryPlainApplication>(*args)
}

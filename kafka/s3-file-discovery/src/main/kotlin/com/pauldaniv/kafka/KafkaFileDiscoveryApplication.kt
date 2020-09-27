package com.pauldaniv.kafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KafkaFileDiscoveryApplication

fun main(args: Array<String>) {
	runApplication<KafkaFileDiscoveryApplication>(*args)
}

package com.pauldaniv.kafkaservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class KafkaFileHandlingApplication {

	@RestController
	class Test {
		@GetMapping
		fun test() = "aoue"
	}
}

fun main(args: Array<String>) {
	runApplication<KafkaFileHandlingApplication>(*args)
}

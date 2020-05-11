package com.paul.dealer.integration

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan

@TestConfiguration
@ComponentScan(
    "com.paul.dealer.*"
)
open class CreateOrderTestConfiguration

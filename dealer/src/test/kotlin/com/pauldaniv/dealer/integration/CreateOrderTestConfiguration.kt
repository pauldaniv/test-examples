package com.pauldaniv.dealer.integration

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan

@TestConfiguration
@ComponentScan(
    "com.pauldaniv.dealer.*"
)
open class CreateOrderTestConfiguration

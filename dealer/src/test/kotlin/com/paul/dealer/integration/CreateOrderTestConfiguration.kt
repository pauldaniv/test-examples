package com.paul.dealer.integration

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(
    "com.paul.common",
    "com.paul.dealer.conf",
    "com.paul.dealer.service",
    "com.paul.dealer.persistence"
)
open class CreateOrderTestConfiguration

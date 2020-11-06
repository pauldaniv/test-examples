package com.pauldaniv.kafka.discovery.tx.config

import com.pauldaniv.kafka.common.config.CommonKafkaConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(CommonKafkaConfig::class)
class DiscoveryTxConfig

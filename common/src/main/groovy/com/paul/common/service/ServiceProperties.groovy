package com.paul.common.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("service")
class ServiceProperties {
  String message
}

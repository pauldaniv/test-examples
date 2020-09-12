package com.pauldaniv.common.service


import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service

@Service
@EnableConfigurationProperties(ServiceProperties)
class MyService {

  private final ServiceProperties serviceProperties

  MyService(ServiceProperties serviceProperties) {
    this.serviceProperties = serviceProperties
  }

  def message() {
    serviceProperties.message
  }
}

package com.pauldaniv.store.client

import com.pauldaniv.store.client.exception.NotFoundAwareDecoder
import feign.codec.ErrorDecoder
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.pauldaniv.store.client.*")
@EnableFeignClients(basePackages = "com.pauldaniv.store.client.*")
class FeignConfiguration {

  @Bean
  ErrorDecoder errorDecoder() {
    return new NotFoundAwareDecoder()
  }
}

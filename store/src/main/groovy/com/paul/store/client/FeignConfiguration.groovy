package com.paul.store.client

import com.paul.store.client.exception.NotFoundAwareDecoder
import feign.codec.ErrorDecoder
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.paul.store.client.*")
@EnableFeignClients(basePackages = "com.paul.store.client.*")
class FeignConfiguration {

    @Bean
    ErrorDecoder errorDecoder() {
        return new NotFoundAwareDecoder()
    }
}
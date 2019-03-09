package com.paul.store.client

import com.paul.store.client.exception.NotFoundAwareDecoder
import feign.codec.Decoder
import org.springframework.beans.factory.ObjectFactory
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder
import org.springframework.cloud.openfeign.support.SpringDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.paul.store.*")
@EnableFeignClients(basePackages = "com.paul.store.client.*")
class FeignConfiguration {

    @Bean
    Decoder decoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new NotFoundAwareDecoder(new ResponseEntityDecoder(new SpringDecoder(messageConverters)))
    }

}
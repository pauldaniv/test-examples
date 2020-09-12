package com.pauldaniv.interservice;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.pauldaniv.*")
@EnableAutoConfiguration
@EnableFeignClients(basePackages = "com.pauldaniv.interservice.common.client.*")
public class Config {
}

package com.paul.common;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.paul.*")
@EnableAutoConfiguration
@EnableFeignClients(basePackages = "com.paul.common.client.*")
public class Config {
}
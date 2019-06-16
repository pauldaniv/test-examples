package com.paul.statistic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.paul.*")
@EnableFeignClients
public class StatisticApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatisticApplication.class, args);
    }
}

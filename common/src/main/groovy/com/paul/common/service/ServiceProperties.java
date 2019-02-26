package com.paul.common.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("service")
public class ServiceProperties {

    private String message;

    String getMessage() {
        return message;
    }

    void setMessage(String message) {
        this.message = message;
    }
}

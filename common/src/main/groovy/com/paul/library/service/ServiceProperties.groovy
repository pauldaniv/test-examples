package com.paul.library.service

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("service")
class ServiceProperties {

    private String message

    String getMessage() {
        return message
    }

    void setMessage(String message) {
        this.message = message
    }
}

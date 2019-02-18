package com.paul.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication(scanBasePackages = "com.paul.store")
@RestController
public class StoreApplication {

//    private final Client client;
//
//    public StoreApplication(Client client) {
//
//        this.client = client;
//    }

    @GetMapping("/")
    public Response home() {
        return new Response() {
            @Override
            public String getName() {
                return "response value";
            }

            @Override
            public String getValue() {
                return "oet";
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }


    private interface Response {
        String getName();
        String getValue();
    }
}

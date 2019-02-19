package com.paul.dealer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication(scanBasePackages = "com.paul.dealer")
@RestController
public class DealerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealerApplication.class, args);
    }

    @GetMapping("/index")
    public String home() {
        return "my response 2";
    }
}

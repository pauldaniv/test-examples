package com.paul.store;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="my-client", url = "http://localhost:8080")
interface Client {
    @GetMapping("/index")
    String index();
}
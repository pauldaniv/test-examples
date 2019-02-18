package com.paul.store;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(url = "http://localhost:8080")
interface Client {
    String index();
}
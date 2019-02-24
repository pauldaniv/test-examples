package com.paul.library.client;

import com.paul.library.payload.Resp;
import com.paul.library.payload.TestEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "${feign.client.dealer.name}", url = "${feign.client.dealer.url}")
public interface DealerClient {

    @GetMapping("/{id}")
    ResponseEntity<Resp<TestEntityDto>> getOne(@PathVariable("id") Long id);

    @PostMapping(value = "/save")
    ResponseEntity<Resp<TestEntityDto>> save(@RequestBody TestEntityDto entity);

    @GetMapping("/all")
    List<TestEntityDto> getAll();

    @GetMapping("/")
    default String index() {
        return "ok";
    }
}
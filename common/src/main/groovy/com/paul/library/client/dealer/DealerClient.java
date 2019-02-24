package com.paul.library.client.dealer;

import com.paul.library.payload.Resp;
import com.paul.library.payload.TestEntityDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "${feign.client.dealer.name}", url = "${feign.client.dealer.url}")
@RequestMapping("/test-entity")
public interface DealerClient {

    @GetMapping("/{id}")
    ResponseEntity<Resp<TestEntityDto>> getOne(@PathVariable("id") Long id);

    @PutMapping("/update")
    ResponseEntity<Resp<TestEntityDto>> update(@RequestBody TestEntityDto entity);

    @PostMapping(value = "/save")
    ResponseEntity<Resp<TestEntityDto>> save(@RequestBody TestEntityDto entity);

    @GetMapping("/all")
    ResponseEntity<Resp<List<TestEntityDto>>> getAll();

    @GetMapping("/healthCheck")
    ResponseEntity<Resp> healthCheck();
}
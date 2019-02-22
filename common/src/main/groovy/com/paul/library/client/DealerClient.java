package com.paul.library.client;

import com.paul.library.domain.TestEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name="my-client", url = "http://localhost:8080/api/dealer")
public interface DealerClient {

    @GetMapping("/{id}")
    TestEntity getOne(@PathVariable("id") Long id);

    @PostMapping(value = "/save")
    String save(TestEntity entity);

    @GetMapping("/all")
    List<TestEntity> getAll();

    @GetMapping("/")
    default String index() {
        return "ok";
    }
}
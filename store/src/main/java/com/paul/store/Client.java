package com.paul.store;

import com.paul.library.domen.TestEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name="my-client", url = "http://localhost:8080")
public interface Client {


    @GetMapping("/index")
    String index();

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    String save(TestEntity entity);

    @GetMapping("/all")
    List<TestEntity> getAll();
}
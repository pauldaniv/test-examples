package com.paul.store;

import com.paul.library.domen.TestEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="my-client", url = "http://localhost:8080/api/dealer")
public interface Client {


//    @GetMapping("/index")
//    Metadata index();

    @PostMapping(value = "/save")
    String save(@RequestBody TestEntity entity);

    @GetMapping("/all")
    List<TestEntity> getAll();
}
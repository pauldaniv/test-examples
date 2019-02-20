package com.paul.store.controller;

import com.paul.library.domen.TestEntity;
import com.paul.store.Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StoreController {

  private final Client client;

  public StoreController(Client client) {
    this.client = client;
  }

  @GetMapping(value = "/all")
  public List<TestEntity> all() {
    return client.getAll();
  }

  @PostMapping(value = "/save")
  public String save(@RequestBody TestEntity entity) {
    return client.save(entity);
  }

  @GetMapping("/")
  public TestEntity home() {
    return new TestEntity().setFirstName("test").setLastName("lastName");
  }
}

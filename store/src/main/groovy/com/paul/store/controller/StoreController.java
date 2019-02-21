package com.paul.store.controller;

import com.paul.library.domain.TestEntity;
import com.paul.store.DealerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StoreController {

  private final DealerService dealer;

  public StoreController(DealerService dealer) {
    this.dealer = dealer;
  }

  @GetMapping(value = "/all")
  public List<TestEntity> all() {
    return dealer.getAll();
  }

  @PostMapping(value = "/save")
  public String save(@RequestBody TestEntity entity) {
    return dealer.save(entity);
  }

  @GetMapping("/")
  public String home() {
    return dealer.index();
  }
}

package com.paul.store.controller

import com.paul.library.domain.TestEntity
import com.paul.store.DealerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StoreController {

  private final DealerService dealer;

  StoreController(DealerService dealer) {
    this.dealer = dealer
  }

  @GetMapping(value = "/all")
  List<TestEntity> all() {
    return dealer.getAll()
  }

  @PostMapping(value = "/save")
  String save(@RequestBody TestEntity entity) {
    return dealer.save(entity)
  }

  @GetMapping("/")
  String home() {
    return dealer.index()
  }
}

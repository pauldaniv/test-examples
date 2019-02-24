package com.paul.store.controller

import com.paul.library.payload.Resp
import com.paul.library.payload.TestEntityDto
import com.paul.store.service.DealerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/store")
class StoreController {

  private final DealerService dealer;

  StoreController(DealerService dealer) {
    this.dealer = dealer
  }

  @GetMapping("/{id}")
  ResponseEntity<Resp<TestEntityDto>> getOne(@PathVariable("id") Long id) {
    def one = dealer.getOne(id)
    return Resp.ok(one.body.body)
  }

  @GetMapping(value = "/all")
  List<TestEntityDto> all() {
    return dealer.getAll()
  }

  @PostMapping(value = "/save")
  ResponseEntity<Resp<TestEntityDto>> save(@RequestBody TestEntityDto entity) {
    def save = dealer.save(entity)
    return Resp.ok(save.body.body)
  }

  @GetMapping("/")
  String home() {
    return dealer.index()
  }
}

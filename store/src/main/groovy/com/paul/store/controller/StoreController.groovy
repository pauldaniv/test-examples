package com.paul.store.controller

import com.paul.library.payload.Resp
import com.paul.library.payload.TestEntityDto
import com.paul.store.service.DealerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import static com.paul.library.payload.Resp.ok

@RestController
@RequestMapping("/api/store")
class StoreController {

  private final DealerService dealer

  StoreController(DealerService dealer) {
    this.dealer = dealer
  }

  @GetMapping("/{id}")
  ResponseEntity<Resp<TestEntityDto>> getOne(@PathVariable("id") Long id) {
    def body = dealer.getOne(id).body
    ok(body.body, body.message, body.success)
  }

  @GetMapping(value = "/all")
  ResponseEntity<Resp<List<TestEntityDto>>> all() {
    def body = dealer.getAll().body
    ok(body.message, body.body)
  }

  @PutMapping("/update")
  ResponseEntity<Resp<TestEntityDto>> update(@RequestBody TestEntityDto dto) {
    def body = dealer.update(dto).body
    ok(body.body, body.message, body.success)
  }

  @PostMapping(value = "/save")
  ResponseEntity<Resp<TestEntityDto>> save(@RequestBody TestEntityDto entity) {
    ok(dealer.save(entity).body.body)
  }

  @GetMapping("/crossHealthCheck")
  ResponseEntity<Resp> home() {
    def body = dealer.index().body
    ok(body.message, body.success)
  }
}

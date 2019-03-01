package com.paul.store.controller

import com.paul.common.payload.Resp
import com.paul.common.payload.TestEntityDto
import com.paul.store.service.DealerService
import com.paul.store.service.DealerServiceImpl
import groovy.util.logging.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import static com.paul.common.payload.Resp.ok

@RestController
@RequestMapping("/api/store")
@Slf4j
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

package com.paul.store.controller

import com.paul.common.payload.TestEntityDto
import com.paul.common.service.MyService
import com.paul.store.service.DealerService
import groovy.util.logging.Slf4j
import org.springframework.web.bind.annotation.*

import static com.paul.common.payload.Resp.ok

@RestController
@RequestMapping("/api/store")
@Slf4j
class StoreController {

  private final DealerService dealer
  private final MyService myService

  StoreController(DealerService dealer, MyService myService) {
    this.dealer = dealer
    this.myService = myService
  }

  @GetMapping("/{id}")
  getOne(@PathVariable("id") Long id) {
    log.debug("MyMessage: ${myService.message()}")
    dealer.getOne(id).body
  }

  @GetMapping(value = "/all")
  all() {
    def body = dealer.getAll().body
    ok(body.message, body.body)
  }

  @PutMapping("/update")
  update(@RequestBody TestEntityDto dto) {
    def body = dealer.update(dto).body
    ok(body.body, body.message, body.success)
  }

  @PostMapping(value = "/save")
  save(@RequestBody TestEntityDto entity) {
    ok(dealer.save(entity).body.body)
  }

  @GetMapping("/crossHealthCheck")
  home() {
    def body = dealer.index().body
    ok(body.message, body.success)
  }
}

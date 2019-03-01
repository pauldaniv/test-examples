package com.paul.dealer.controller

import com.paul.common.payload.Resp
import com.paul.common.payload.TestEntityDto
import com.paul.dealer.service.DefaultService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/dealer/test-entity")
class TestEntityController {

  private final DefaultService service

  TestEntityController(DefaultService defaultService) {
    this.service = defaultService
  }

  @GetMapping("/{id}")
  ResponseEntity getOne(@PathVariable("id") Long id) {
    service.getOne(id)
  }

  @GetMapping("/all")
  ResponseEntity<Resp<List<TestEntityDto>>> getAll() {
    def all = service.getAll()
    all
  }

  @PutMapping("/update")
  ResponseEntity update(@RequestBody TestEntityDto entity) {
    service.update(entity)
  }

  @PostMapping("/save")
  ResponseEntity save(@RequestBody TestEntityDto entity) {
    service.save(entity)
  }
}

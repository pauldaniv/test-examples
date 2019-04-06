package com.paul.dealer.controller

import com.paul.common.payload.Resp
import com.paul.common.payload.TestEntityDto
import com.paul.dealer.service.DefaultServiceImpl
import groovy.transform.TypeChecked
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/dealer/test-entity")
@TypeChecked
class TestEntityController {

  private final DefaultServiceImpl service

  TestEntityController(DefaultServiceImpl defaultService) {
    this.service = defaultService
  }

  @GetMapping("/{id}")
  ResponseEntity getOne(@PathVariable("id") Long id) {

    service.getOne(id)
    Resp.ok("testMessage", HttpStatus.UNAUTHORIZED)
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

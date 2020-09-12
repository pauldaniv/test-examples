package com.pauldaniv.dealer.controller

import com.pauldaniv.common.payload.Resp
import com.pauldaniv.common.payload.TestEntityDto
import com.pauldaniv.dealer.service.DefaultService
import groovy.transform.TypeChecked
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/dealer/test-entity")
@TypeChecked
class TestEntityController {

  final DefaultService service

  TestEntityController(DefaultService defaultService) {
    this.service = defaultService
  }

  @GetMapping("/{id}")
  ResponseEntity getOne(@PathVariable("id") Long id) {

    service.getOne(id)
    Resp.ok("testMessage", HttpStatus.UNAUTHORIZED)
  }

  @GetMapping("/all")
  ResponseEntity<Resp<List<TestEntityDto>>> getAll() {
    service.getAll()
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

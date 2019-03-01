package com.paul.common.client.dealer

import com.paul.common.payload.Resp
import com.paul.common.payload.TestEntityDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@FeignClient(name = "dealerClient", url = '${feign.client.dealer.url}')
@RequestMapping("/test-entity")
interface DealerClient {

  @GetMapping("/{id}")
  ResponseEntity<Resp<TestEntityDto>> getOne(@PathVariable("id") Long id)

  @PutMapping("/update")
  ResponseEntity<Resp<TestEntityDto>> update(@RequestBody TestEntityDto entity)

  @PostMapping(value = "/save")
  ResponseEntity<Resp<TestEntityDto>> save(@RequestBody TestEntityDto entity)

  @GetMapping("/all")
  ResponseEntity<Resp<List<TestEntityDto>>> getAll()

  @GetMapping("/healthCheck")
  ResponseEntity<Resp> healthCheck()
}

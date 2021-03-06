package com.pauldaniv.interservice.common.client.dealer

import com.pauldaniv.common.payload.CarDto
import com.pauldaniv.common.payload.CarSearchParams
import com.pauldaniv.common.payload.IdsList
import com.pauldaniv.common.payload.Resp
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@FeignClient(name = 'carClient', url = '${feign.client.dealer.url}')
@RequestMapping("/cars")
interface CarClient {

  @GetMapping("/info/{id}")
  ResponseEntity<Resp<CarDto>> info(@PathVariable("id") Long id)

  @GetMapping("/book/{id}")
  ResponseEntity<Resp> book(@PathVariable("id") Long id)

  @PostMapping("/book")
  ResponseEntity<Resp> bookMany(@RequestBody IdsList ids)

  @GetMapping("/buy/{id}")
  ResponseEntity<Resp> buyOne(@PathVariable("id") Long id)

  @PostMapping("/buy")
  ResponseEntity<Resp> buyMany(@RequestBody IdsList ids)

  @PatchMapping("/search")
  ResponseEntity<Resp> search(@RequestBody CarSearchParams search)
}

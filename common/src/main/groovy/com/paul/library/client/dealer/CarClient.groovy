package com.paul.library.client.dealer

import com.paul.library.payload.IdsList
import com.paul.library.payload.SearchParams
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@FeignClient(name = 'carClient', url = '${feign.client.dealer.url}')
@RequestMapping("/cars")
interface CarClient {

    @PatchMapping("/search")
    ResponseEntity search(@RequestBody SearchParams dto)

    @GetMapping("/info/{id}")
    ResponseEntity info(@PathVariable("id") Long id)

    @GetMapping("/{id}")

    ResponseEntity book(@PathVariable("id") Long id)

    @GetMapping("/buy/{id}")
    ResponseEntity buyOne(@PathVariable("id") Long id)

    @PostMapping("/buy")
    ResponseEntity buyBulk(@RequestBody IdsList ids)
}
package com.paul.common.client.dealer

import com.paul.common.payload.Resp
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@FeignClient(name = 'invoiceClient', url = '${feign.client.dealer.url')
@RequestMapping("/invoices")
interface InvoiceClient {

  @GetMapping("/pending/{id}")
  ResponseEntity<Resp> getPending(@PathVariable("id") Long userId)

  @GetMapping("/latest-payed/{id}")
  getLatestPayed(@PathVariable("id") Long id)
}

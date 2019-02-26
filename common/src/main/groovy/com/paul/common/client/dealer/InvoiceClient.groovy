package com.paul.common.client.dealer

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@FeignClient(name = 'invoiceClient', url = '${feign.client.dealer.url')
@RequestMapping("/invoices")
interface InvoiceClient {

    @GetMapping("/pending/{id}")
    getPending(@PathVariable("id") Long userId)

    @GetMapping("/latest-payed/{id}")
    getLatestPayed(@PathVariable("id") Long id)
}

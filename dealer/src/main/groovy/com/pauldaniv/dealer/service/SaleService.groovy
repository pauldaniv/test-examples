package com.pauldaniv.dealer.service

import com.pauldaniv.common.payload.Resp
import org.springframework.http.ResponseEntity

interface SaleService {

  ResponseEntity<Resp> buyOne(Long customerId, Long carId)

  ResponseEntity<Resp> buyOne(Long customerId, List<Long> carsIds)

}

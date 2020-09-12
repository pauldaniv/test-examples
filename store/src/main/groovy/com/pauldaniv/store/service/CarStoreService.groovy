package com.pauldaniv.store.service

import com.pauldaniv.common.payload.CarDto
import com.pauldaniv.common.payload.CarSearchParams
import com.pauldaniv.common.payload.IdsList
import com.pauldaniv.common.payload.Resp
import org.springframework.http.ResponseEntity

interface CarStoreService {
  ResponseEntity<Resp<CarDto>> getCarInfo(Long carId)

  ResponseEntity<Resp<List<CarDto>>> getManyCarCarInfo(Long carId)

  ResponseEntity<Resp> bookOne(Long carId)

  ResponseEntity<Resp> bookMany(IdsList ids)

  ResponseEntity<Resp<List<CarDto>>> search(CarSearchParams search)
}

package com.pauldaniv.dealer.service

import com.pauldaniv.common.payload.CarDto
import com.pauldaniv.dealer.domain.Car
import org.springframework.http.ResponseEntity

interface CarService extends CommonService<CarDto, Car> {
  ResponseEntity updateCarCount(Long carId, Integer count)
}

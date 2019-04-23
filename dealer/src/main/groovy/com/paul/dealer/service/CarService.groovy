package com.paul.dealer.service

import com.paul.common.payload.CarDto
import org.springframework.http.ResponseEntity

interface CarService extends CommonService<CarDto> {
  ResponseEntity updateCarCount(Long carId, Integer count)
}

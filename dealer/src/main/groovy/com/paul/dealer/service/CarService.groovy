package com.paul.dealer.service

import com.paul.common.payload.CarDto
import com.paul.dealer.domain.Car
import org.springframework.http.ResponseEntity

interface CarService extends CommonService<CarDto, Car> {
  ResponseEntity updateCarCount(Long carId, Integer count)
}

package com.paul.dealer.service

import com.paul.dealer.domain.Car
import com.paul.dealer.persintence.CarRepository
import com.paul.library.payload.CarDto
import org.springframework.stereotype.Service

@Service
class CarService extends AbstractCommonService<CarDto, Car, CarRepository>{
  CarService(CarRepository repository) {
    super(repository)
  }
}

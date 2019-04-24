package com.paul.dealer.service

import com.paul.common.component.Mapper
import com.paul.common.payload.CarDto
import com.paul.common.payload.Resp
import com.paul.dealer.domain.Car
import com.paul.dealer.persintence.CarRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

import java.security.InvalidParameterException

@Service
class CarServiceImpl extends AbstractCommonService<CarDto, Car, CarRepository> implements CarService {
  private final CarRepository repository
  private final Mapper map

  @Value('${dealer.service.car.price.margin.high}')
  private final Double marginHigh

  @Value('${dealer.service.car.price.margin.low}')
  private final Double marginLow

  CarServiceImpl(CarRepository repository, Mapper map) {
    super(repository)
    this.repository = repository
    this.map = map
  }

  @Override
  ResponseEntity getOne(Long id) {
    def car = repository.findOne(id)
    def carDto = map.map(car, dtoType)
    carDto.available = carDto.count > 0
    if (carDto.count > 100) {
      carDto.price += carDto.price * marginHigh
    } else if (carDto.count < 10) {
      carDto.price += carDto.price * marginLow
    }
    Resp.ok(carDto)
  }

  @Override
  ResponseEntity updateCarCount(Long carId, Integer count) {
    def countValue = OptionalInt.of(count).orElseThrow({
      new InvalidParameterException("Invalid parameter count: $count")
    })
    if (countValue < 0)
      new InvalidParameterException("Invalid parameter count. Value must be >= 0. Got: $count")
    def car = repository.findOne(carId)
    car.count = countValue
    def saved = repository.save(car)
    Resp.ok(map.map(saved, dtoType))
  }
}

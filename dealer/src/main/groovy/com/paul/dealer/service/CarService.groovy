package com.paul.dealer.service

import com.paul.common.component.Mapper
import com.paul.common.payload.CarDto
import com.paul.common.payload.Resp
import com.paul.dealer.domain.Car
import com.paul.dealer.persintence.CarRepository
import org.hibernate.ObjectNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CarService extends AbstractCommonService<CarDto, Car, CarRepository> {
  private final CarRepository repository
  private final Mapper map

  CarService(CarRepository repository, Mapper map) {
    super(repository)
    this.repository = repository
    this.map = map
  }

  @Override
  ResponseEntity getOne(Long id) {

    def car = repository.findById(id).orElseThrow({new ObjectNotFoundException(id, entityType.simpleName)})
    def carDto = map.map(car, dtoType)
    if (car.count < 1) {
      carDto.available = false
      Resp.ok(carDto)
    } else Resp.ok(carDto)
  }
}

package com.pauldaniv.dealer.controller

import com.pauldaniv.common.payload.CarDto
import com.pauldaniv.dealer.service.CarServiceImpl
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/dealer/cars")
class CarDealerController {

  private final CarServiceImpl carService

  CarDealerController(CarServiceImpl carService) {
    this.carService = carService
  }

  @GetMapping("/info/{id}")
  getOne(@PathVariable("id") Long id) {
    def one = carService.getOne(id)
    one
  }

  @PostMapping
  save(@RequestBody CarDto car) {
    carService.save(car)
  }
}

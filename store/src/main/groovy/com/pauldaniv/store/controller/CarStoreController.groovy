package com.pauldaniv.store.controller


import com.pauldaniv.store.service.CarStoreService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/store/cars")
class CarStoreController {

  private final CarStoreService carService

  CarStoreController(CarStoreService carService) {
    this.carService = carService
  }

  @GetMapping("/{id}")
  getCarInfo(@PathVariable("id") Long carId) {
    def info = carService.getCarInfo(carId)
    info
  }
}

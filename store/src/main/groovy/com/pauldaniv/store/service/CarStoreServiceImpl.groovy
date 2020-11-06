package com.pauldaniv.store.service

import com.pauldaniv.common.payload.CarDto
import com.pauldaniv.common.payload.CarSearchParams
import com.pauldaniv.common.payload.IdsList
import com.pauldaniv.common.payload.Resp
import com.pauldaniv.interservice.common.client.dealer.CarClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CarStoreServiceImpl implements CarStoreService {

  private final CarClient client

  @Value('${store.service.car.price.margin}')
  private Double margin

  CarStoreServiceImpl(final CarClient client) {
    this.client = client
  }

  @Override
  ResponseEntity<Resp<CarDto>> getCarInfo(final Long carId) {
    def carInfo = client.info(carId)
    if (carInfo.body.success) {
      Double carPrice = carInfo.body.body.price
      carInfo.body.body.price += carPrice * margin
      return Resp.ok(carInfo.body.body)
    } else
      return Resp.ok(carInfo.body.message, carInfo.body.success)
  }

  @Override
  ResponseEntity<Resp<List<CarDto>>> getManyCarCarInfo(final Long carId) {
    return null
  }

  @Override
  ResponseEntity<Resp> bookOne(final Long carId) {
    return null
  }

  @Override
  ResponseEntity<Resp> bookMany(final IdsList ids) {
    return null
  }

  @Override
  ResponseEntity<Resp<List<CarDto>>> search(final CarSearchParams search) {
    return null
  }
}

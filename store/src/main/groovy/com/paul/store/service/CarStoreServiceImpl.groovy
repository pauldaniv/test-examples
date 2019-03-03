package com.paul.store.service

import com.paul.common.client.dealer.CarClient
import com.paul.common.payload.CarDto
import com.paul.common.payload.CarSearchParams
import com.paul.common.payload.IdsList
import com.paul.common.payload.Resp
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class CarStoreServiceImpl implements CarStoreService {

    private final CarClient client

    @Value('${store.service.car.price.margin}')
    private Integer margin

    CarStoreServiceImpl(CarClient client) {
        this.client = client
    }

    @Override
    ResponseEntity<Resp<CarDto>> getCarInfo(Long carId) {
        def carInfo = client.info(carId)
        if (carInfo.body.success) {
            def carPrice = carInfo.body.body.price
            carInfo.body.body.price += (carPrice / 100) * margin
           return Resp.ok(carInfo.body.body)
        } else
            return Resp.ok(carInfo.body.message, carInfo.body.success)
    }

    @Override
    ResponseEntity<Resp<List<CarDto>>> getManyCarCarInfo(Long carId) {
        return null
    }

    @Override
    ResponseEntity<Resp> bookOne(Long carId) {
        return null
    }

    @Override
    ResponseEntity<Resp> bookMany(IdsList ids) {
        return null
    }

    @Override
    ResponseEntity<Resp<List<CarDto>>> search(CarSearchParams search) {
        return null
    }
}

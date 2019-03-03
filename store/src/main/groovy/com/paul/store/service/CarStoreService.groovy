package com.paul.store.service

import com.paul.common.payload.CarDto
import com.paul.common.payload.IdsList
import com.paul.common.payload.Resp
import com.paul.common.payload.CarSearchParams
import org.springframework.http.ResponseEntity

interface CarStoreService {
    ResponseEntity<Resp<CarDto>> getCarInfo(Long carId)
    ResponseEntity<Resp<List<CarDto>>> getManyCarCarInfo(Long carId)
    ResponseEntity<Resp> bookOne(Long carId)
    ResponseEntity<Resp> bookMany(IdsList ids)
    ResponseEntity<Resp<List<CarDto>>> search(CarSearchParams search)
}

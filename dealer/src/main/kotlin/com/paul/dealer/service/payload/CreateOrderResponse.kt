package com.paul.dealer.service.payload

import com.paul.common.payload.CarDto

data class CreateOrderResponse(
    var notAvailableCars: List<CarDto>? = null,
    var orderedCars: List<CarDto>? = null
)

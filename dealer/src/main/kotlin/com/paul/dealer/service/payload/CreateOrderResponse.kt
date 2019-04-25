package com.paul.dealer.service.payload

import com.paul.common.payload.CarDto
import com.paul.common.payload.OrderDto

data class CreateOrderResponse(
    var notAvailableCars: List<CarDto>? = null,
    var orderedCars: List<CarDto>? = null,
    var order: OrderDto? = null
)

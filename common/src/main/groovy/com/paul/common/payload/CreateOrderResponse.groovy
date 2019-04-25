package com.paul.common.payload

import lombok.Builder
import lombok.EqualsAndHashCode

@Builder
@EqualsAndHashCode
class CreateOrderResponse {
  List<CarDto> notAvailableCars
  List<CarDto> orderedCars
  OrderDto order
}

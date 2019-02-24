package com.paul.library.payload

import com.paul.library.payload.base.WithDateDto
import groovy.transform.builder.Builder

import java.time.LocalDate

@Builder
class CarDto extends WithDateDto {
  String brand
  String model
  LocalDate releasedIn
  List<OrderDto> orders
}

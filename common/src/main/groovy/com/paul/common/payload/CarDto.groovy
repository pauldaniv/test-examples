package com.paul.common.payload

import com.paul.common.payload.base.WithDateDto
import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder

import java.time.LocalDate

@Builder
@EqualsAndHashCode
class CarDto extends WithDateDto {
  String brand
  String model
  LocalDate releasedIn
  List<OrderDto> orders
}

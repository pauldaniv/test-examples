package com.paul.library.payload

import com.paul.library.domain.base.WithDate
import groovy.transform.builder.Builder

import java.time.LocalDate

@Builder
class CarDto extends WithDate<CarDto> {
  String brand;
  String model;
  LocalDate releasedIn;
  List<OrderDto> orders;
}

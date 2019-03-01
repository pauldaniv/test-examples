package com.paul.common.payload

import com.paul.common.payload.base.WithIdDto
import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder

@Builder
@EqualsAndHashCode
class OrderDto extends WithIdDto {
  InvoiceDto invoice
  List<CarDto> cars
}

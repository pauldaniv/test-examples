package com.paul.library.payload

import com.paul.library.payload.base.WithIdDto
import groovy.transform.builder.Builder

@Builder
class OrderDto extends WithIdDto {
  InvoiceDto invoice;
  List<CarDto> cars;
}

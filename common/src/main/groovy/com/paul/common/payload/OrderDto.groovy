package com.paul.common.payload

import com.paul.common.payload.base.WithIdDto
import groovy.transform.builder.Builder

@Builder
class OrderDto extends WithIdDto {
  InvoiceDto invoice;
  List<CarDto> cars;
}

package com.pauldaniv.common.payload

import com.pauldaniv.common.payload.base.WithIdDto
import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder

@Builder
@EqualsAndHashCode
class OrderDto extends WithIdDto {
  InvoiceDto invoice
  CustomerDto customer
  List<CarDto> cars
}

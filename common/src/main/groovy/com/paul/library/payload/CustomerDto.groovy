package com.paul.library.payload

import com.paul.library.payload.base.WithIdDto
import groovy.transform.builder.Builder

@Builder
class CustomerDto extends WithIdDto {

  String fullName
  List<OrderDto> orders
  List<InvoiceDto> invoices
}

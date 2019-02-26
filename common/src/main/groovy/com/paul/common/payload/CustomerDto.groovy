package com.paul.common.payload

import com.paul.common.payload.base.WithIdDto
import groovy.transform.builder.Builder

@Builder
class CustomerDto extends WithIdDto {

  String fullName
  List<OrderDto> orders
  List<InvoiceDto> invoices
}

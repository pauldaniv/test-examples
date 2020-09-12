package com.pauldaniv.common.payload

import com.pauldaniv.common.payload.base.WithIdDto
import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder

@Builder
@EqualsAndHashCode
class CustomerDto extends WithIdDto {

  String fullName
  List<OrderDto> orders
  List<InvoiceDto> invoices
}

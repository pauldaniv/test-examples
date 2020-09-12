package com.pauldaniv.common.payload

import com.pauldaniv.common.payload.base.WithDateDto
import groovy.transform.builder.Builder
import lombok.EqualsAndHashCode

@Builder
@EqualsAndHashCode
class InvoiceDto extends WithDateDto {
  Double total
  List<OrderDto> orders
  CustomerDto customer
}

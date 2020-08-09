package com.paul.common.payload


import com.paul.common.payload.base.WithDateDto
import groovy.transform.builder.Builder
import lombok.EqualsAndHashCode

@Builder
@EqualsAndHashCode
class InvoiceDto extends WithDateDto {
  Double total
  List<OrderDto> orders
  CustomerDto customer
}

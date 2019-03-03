package com.paul.common.payload


import com.paul.common.payload.base.WithDateDto
import groovy.transform.builder.Builder
import lombok.EqualsAndHashCode

@Builder
@EqualsAndHashCode
class InvoiceDto extends WithDateDto {
  Integer total
  List<OrderDto> orders
  CustomerDto customer
}

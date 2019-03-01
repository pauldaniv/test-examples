package com.paul.common.payload


import com.paul.common.payload.base.WithDateDto
import groovy.transform.builder.Builder

@Builder
class InvoiceDto extends WithDateDto {
  private Integer total
  private OrderDto order
  private CustomerDto customer
}

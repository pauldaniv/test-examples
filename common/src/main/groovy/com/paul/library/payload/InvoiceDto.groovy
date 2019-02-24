package com.paul.library.payload


import com.paul.library.payload.base.WithDateDto
import groovy.transform.builder.Builder

@Builder
class InvoiceDto extends WithDateDto {
  private Integer total;
  private OrderDto order;
  private CustomerDto customer;
}

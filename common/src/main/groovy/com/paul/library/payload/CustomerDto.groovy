package com.paul.library.payload

import com.paul.library.payload.base.WithIdDto

class CustomerDto extends WithIdDto<CustomerDto> {

  String fullName
  List<OrderDto> orders
  List<InvoiceDto> invoices
}

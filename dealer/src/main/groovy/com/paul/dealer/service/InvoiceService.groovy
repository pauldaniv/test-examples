package com.paul.dealer.service

import com.paul.common.payload.InvoiceDto
import com.paul.common.payload.Resp
import com.paul.dealer.domain.Invoice
import org.springframework.http.ResponseEntity

interface InvoiceService extends CommonService<InvoiceDto, Invoice> {

  ResponseEntity<Resp<InvoiceDto>> createInvoice(List<Long> orders, Long customerId)
}

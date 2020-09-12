package com.pauldaniv.dealer.service

import com.pauldaniv.common.payload.InvoiceDto
import com.pauldaniv.common.payload.Resp
import com.pauldaniv.dealer.domain.Invoice
import org.springframework.http.ResponseEntity

interface InvoiceService extends CommonService<InvoiceDto, Invoice> {

  ResponseEntity<Resp<InvoiceDto>> createInvoice(List<Long> orders, Long customerId)
}

package com.paul.dealer.service

import com.paul.common.payload.InvoiceDto
import com.paul.dealer.domain.Invoice
import com.paul.dealer.persintence.InvoiceRepository
import org.springframework.stereotype.Service

@Service
class InvoiceService extends AbstractCommonService<InvoiceDto, Invoice, InvoiceRepository> {
  InvoiceService(InvoiceRepository repository) {
    super(repository)
  }
}

package com.paul.dealer.service

import com.paul.dealer.domain.Invoice
import com.paul.dealer.persintence.InvoiceRepository
import com.paul.library.payload.InvoiceDto
import org.springframework.stereotype.Service

@Service
class InvoiceService extends AbstractCommonService<InvoiceDto, Invoice, InvoiceRepository> {
  InvoiceService(InvoiceRepository repository) {
    super(repository)
  }
}

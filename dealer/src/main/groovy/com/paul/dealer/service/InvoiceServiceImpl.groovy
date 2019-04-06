package com.paul.dealer.service

import com.paul.common.payload.InvoiceDto
import com.paul.dealer.domain.Invoice
import com.paul.dealer.persintence.InvoiceRepository
import org.springframework.stereotype.Service

@Service
class InvoiceServiceImpl extends AbstractCommonService<InvoiceDto, Invoice, InvoiceRepository> implements InvoiceService {
  InvoiceServiceImpl(InvoiceRepository repository) {
    super(repository)
  }
}

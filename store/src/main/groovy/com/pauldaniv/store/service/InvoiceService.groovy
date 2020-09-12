package com.pauldaniv.store.service

import com.pauldaniv.common.payload.Resp
import com.pauldaniv.interservice.common.client.dealer.InvoiceClient
import org.springframework.http.ResponseEntity

class InvoiceService {

  private final InvoiceClient invoiceClient

  InvoiceService(InvoiceClient invoiceClient) {
    this.invoiceClient = invoiceClient
  }

  private final
  ResponseEntity<Resp> findPendingInvoices(final Long userId) {
    invoiceClient.getPending(userId)
  }
}

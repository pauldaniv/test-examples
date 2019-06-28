package com.paul.store.service

import com.paul.common.payload.Resp
import com.paul.interservice.common.client.dealer.InvoiceClient
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

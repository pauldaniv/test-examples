package com.paul.store.service

import com.paul.common.client.dealer.InvoiceClient
import com.paul.common.payload.Resp
import org.springframework.http.ResponseEntity

class InvoiceService {

  private final InvoiceClient invoiceClient

  InvoiceService(InvoiceClient invoiceClient) {
    this.invoiceClient = invoiceClient
  }

  private final
  ResponseEntity<Resp> findPendingInvoices(Long userId) {
    invoiceClient.getPending(userId)
  }
}

package com.paul.dealer.service

import com.paul.dealer.persintence.InvoiceRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class InvoiceService {

  final InvoiceRepository invoiceRepository
}

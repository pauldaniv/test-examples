package com.pauldaniv.dealer.service

import com.pauldaniv.common.component.Mapper
import com.pauldaniv.common.payload.InvoiceDto
import com.pauldaniv.common.payload.Resp
import com.pauldaniv.dealer.domain.Invoice
import com.pauldaniv.dealer.domain.Order
import com.pauldaniv.dealer.persistence.CustomerRepository
import com.pauldaniv.dealer.persistence.InvoiceRepository
import com.pauldaniv.dealer.persistence.OrderRepository
import org.hibernate.ObjectNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

import javax.transaction.Transactional
import java.security.InvalidParameterException

@Service
class InvoiceServiceImpl extends AbstractCommonService<InvoiceDto, Invoice, InvoiceRepository>
    implements InvoiceService {

  private InvoiceRepository invoiceRepository
  private CustomerRepository customerRepository
  private OrderRepository orderRepository
  private Mapper map

  InvoiceServiceImpl(InvoiceRepository invoiceRepository,
      CustomerRepository customerRepository,
      OrderRepository orderRepository,
      Mapper map) {
    super(invoiceRepository)
    this.invoiceRepository = invoiceRepository
    this.customerRepository = customerRepository
    this.orderRepository = orderRepository
    this.map = map
  }

  @Override
  @Transactional
  ResponseEntity<Resp<InvoiceDto>> createInvoice(List<Long> orderIds, Long customerId) {
    if (customerId == null) {
      throw new InvalidParameterException("Customer id must not be null")
    }
    if (orderIds.stream().anyMatch({ it == null })) {
      throw new InvalidParameterException("Car id must not be null")
    }
    def customer = customerRepository.findById(customerId)
        .orElseThrow({ new ObjectNotFoundException(customerId, "Customer") })

    def orders = new ArrayList<Order>()
    orders.addAll(orderRepository.findAllById(orderIds))

    orderIds.forEach({
      if (orders.stream().noneMatch(car -> car.id == it))
        throw new ObjectNotFoundException(it, "Order")
    })

    def ordersSum = orders.stream().mapToDouble({ it.cars.stream().mapToDouble({ it.price }).sum() }).sum()
    def invoice = invoiceRepository.save(
        Invoice.builder()
            .customer(customer)
            .orders(orders)
            .total(ordersSum)
            .build()
    )

    return Resp.ok(map.map(invoice, InvoiceDto))
  }
}

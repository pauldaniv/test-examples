package com.paul.dealer.service

import com.paul.dealer.domain.Order
import com.paul.dealer.persintence.OrderRepository
import com.paul.library.payload.OrderDto
import org.springframework.stereotype.Service

@Service
class OrderService extends AbstractCommonService<OrderDto, Order, OrderRepository>{
  OrderService(OrderRepository repository) {
    super(repository)
  }
}

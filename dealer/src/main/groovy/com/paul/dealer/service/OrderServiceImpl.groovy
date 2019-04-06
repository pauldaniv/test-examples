package com.paul.dealer.service

import com.paul.common.payload.OrderDto
import com.paul.dealer.domain.Order
import com.paul.dealer.persintence.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderServiceImpl extends AbstractCommonService<OrderDto, Order, OrderRepository> implements OrderService {
  OrderServiceImpl(OrderRepository repository) {
    super(repository)
  }
}

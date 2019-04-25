package com.paul.dealer.service

import com.paul.common.payload.OrderDto
import com.paul.common.payload.Resp
import com.paul.dealer.domain.Order
import com.paul.common.payload.CreateOrderResponse
import org.springframework.http.ResponseEntity

interface OrderService extends CommonService<OrderDto, Order> {
  ResponseEntity<Resp<CreateOrderResponse>> createOrder(List<Long> carIds, Long customerId)
}

package com.pauldaniv.dealer.service

import com.pauldaniv.common.payload.CreateOrderResponse
import com.pauldaniv.common.payload.OrderDto
import com.pauldaniv.common.payload.Resp
import com.pauldaniv.dealer.domain.Order
import org.springframework.http.ResponseEntity

interface OrderService extends CommonService<OrderDto, Order> {
  ResponseEntity<Resp<CreateOrderResponse>> createOrder(List<Long> carIds, Long customerId)
}

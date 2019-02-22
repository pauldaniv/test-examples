package com.paul.dealer.service

import com.paul.dealer.persintence.OrderRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class OrderService {
  final OrderRepository orderRepository
}

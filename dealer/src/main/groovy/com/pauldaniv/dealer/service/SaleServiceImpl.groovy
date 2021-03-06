package com.pauldaniv.dealer.service

import com.pauldaniv.common.component.Mapper
import com.pauldaniv.common.payload.OrderDto
import com.pauldaniv.common.payload.Resp
import com.pauldaniv.dealer.domain.Car
import com.pauldaniv.dealer.domain.Customer
import com.pauldaniv.dealer.domain.Order
import com.pauldaniv.dealer.persistence.CarRepository
import com.pauldaniv.dealer.persistence.CustomerRepository
import com.pauldaniv.dealer.persistence.OrderRepository
import org.hibernate.ObjectNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class SaleServiceImpl implements SaleService {

  CarRepository carRepository
  OrderRepository orderRepository
  CustomerRepository customerRepository
  Mapper map

  @Override
  ResponseEntity<Resp> buyOne(Long customerId, Long carId) {
    def car = carRepository.findById(carId).orElseThrow({ new ObjectNotFoundException(carId, Car.simpleName) })
    def customer = customerRepository.findById(customerId).orElseThrow({
      new ObjectNotFoundException(customerId, Customer.simpleName)
    })
    if (car.count > 0) {
      def save = orderRepository.save(Order.builder().cars(List.of(car)).customer(customer).build())
      Resp.ok(map.map(save, OrderDto))
    } else {

    }
    Resp.ok("Car is not available at the moment")
  }

  @Override
  ResponseEntity<Resp> buyOne(Long customerId, List<Long> carsIds) {
    return null
  }
}

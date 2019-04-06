package com.paul.dealer.service

import com.paul.common.component.Mapper
import com.paul.common.payload.OrderDto
import com.paul.common.payload.Resp
import com.paul.dealer.domain.Car
import com.paul.dealer.domain.Customer
import com.paul.dealer.domain.Order
import com.paul.dealer.persintence.CarRepository
import com.paul.dealer.persintence.CustomerRepository
import com.paul.dealer.persintence.OrderRepository
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

package com.paul.dealer.service.impl

import com.paul.common.component.Mapper
import com.paul.common.payload.CarDto
import com.paul.common.payload.OrderDto
import com.paul.common.payload.Resp
import com.paul.dealer.domain.Car
import com.paul.dealer.domain.Order
import com.paul.dealer.persintence.CarRepository
import com.paul.dealer.persintence.CustomerRepository
import com.paul.dealer.persintence.OrderRepository
import com.paul.dealer.service.AbstractCommonService
import com.paul.dealer.service.OrderService
import com.paul.dealer.service.payload.CreateOrderResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
open class OrderServiceImpl(
    private val customerRepository: CustomerRepository,
    private val carRepository: CarRepository,
    orderRepository: OrderRepository,
    private val map: Mapper
) : AbstractCommonService<OrderDto, Order, OrderRepository>(orderRepository), OrderService {
  
  @Transactional
  override fun createOrder(carIds: List<Long>, customerId: Long): ResponseEntity<Resp<CreateOrderResponse>> {
    val customer = customerRepository.findOne(customerId)
    val cars = carRepository.findAll(carIds)
    val notAvailableCars = mutableListOf<Long>()
    
    cars.forEach {
      if (decrementIfAvailable(it)) {
        cars.remove(it)
        notAvailableCars.add(it.id)
      }
    }
    
    val response = CreateOrderResponse()
    if (notAvailableCars.isNotEmpty()) {
      response.notAvailableCars = map.map(carRepository.findAll(notAvailableCars), CarDto::class.java)
    }
    response.orderedCars = map.map(cars, CarDto::class.java)
    saveEntity(Order.builder()
        .cars(cars)
        .customer(customer)
        .build())
    
    return Resp.ok(response)
  }
  
  private fun decrementIfAvailable(car: Car): Boolean {
    return if (car.count > 0) {
      car.count--
      true
    } else {
      false
    }
  }
}

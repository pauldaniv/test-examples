package com.paul.dealer.service

import com.paul.common.component.Mapper
import com.paul.common.payload.CarDto
import com.paul.common.payload.OrderDto
import com.paul.common.payload.Resp
import com.paul.dealer.domain.Car
import com.paul.dealer.domain.Order
import com.paul.dealer.persistence.CarRepository
import com.paul.dealer.persistence.CustomerRepository
import com.paul.dealer.persistence.OrderRepository
import com.paul.dealer.service.payload.CreateOrderResponse
import org.hibernate.ObjectNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

import javax.transaction.Transactional
import java.security.InvalidParameterException
import java.util.stream.Collectors

@Service
class OrderServiceImpl extends AbstractCommonService<OrderDto, Order, OrderRepository> implements OrderService {

  CustomerRepository customerRepository
  CarRepository carRepository
  OrderRepository orderRepository
  Mapper map

  OrderServiceImpl(CustomerRepository customerRepository,
      CarRepository carRepository,
      OrderRepository orderRepository,
      Mapper map) {
    super(orderRepository)
    this.customerRepository = customerRepository
    this.carRepository = carRepository
    this.orderRepository = orderRepository
    this.map = map
  }

  @Transactional
  ResponseEntity<Resp<CreateOrderResponse>> createOrder(List<Long> carIds, Long customerId) {

    if (carIds.stream().anyMatch({ it == null })) {
      throw new InvalidParameterException("Car id must not be null")
    }
    if (customerId == null) {
      throw new InvalidParameterException("Customer id must not be null")
    }

    def customer = customerRepository.findById(customerId).orElseThrow({
      new ObjectNotFoundException(customerId, "Customer")
    })

    def cars = new ArrayList<Car>()
    cars.addAll(carRepository.findAllById(carIds))

    carIds.forEach({ if (cars.stream().noneMatch(car -> car.id == it)) throw new ObjectNotFoundException(it, "Car") })

    def nonExistingCars = cars.stream().filter({ it.count == 0 }).map({ it.id }).collect(Collectors.toList())
    cars.removeIf({ nonExistingCars.contains(it.id) })
    cars.forEach({ --it.count })
    def response = new CreateOrderResponse()

    response.notAvailableCars = map.map(carRepository.findAllById(nonExistingCars), CarDto)

    response.orderedCars = map.map(cars, CarDto)

    def order = saveEntity(Order.builder()
        .cars(cars)
        .customer(customer)
        .build())
    response.order = order.body.body

    return Resp.ok(response)
  }
}

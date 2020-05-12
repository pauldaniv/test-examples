package com.paul.dealer.conf

import com.paul.common.component.Mapper
import com.paul.common.payload.*
import com.paul.common.payload.base.WithIdDto
import com.paul.dealer.domain.*
import com.paul.dealer.domain.base.WithId
import com.paul.dealer.persistence.*
import lombok.EqualsAndHashCode
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component


@Component
class DbInitializer {

  private final Mapper map
  private final CarRepository carRepository
  private final InvoiceRepository invoiceRepository
  private final OrderRepository orderRepository
  private final CustomerRepository customerRepository
  private final DefaultRepository defaultRepository

  DbInitializer(Mapper map,
                CarRepository carRepository,
                InvoiceRepository invoiceRepository,
                OrderRepository orderRepository,
                CustomerRepository customerRepository,
                DefaultRepository defaultRepository) {

    this.map = map
    this.carRepository = carRepository
    this.invoiceRepository = invoiceRepository
    this.orderRepository = orderRepository
    this.customerRepository = customerRepository
    this.defaultRepository = defaultRepository
  }

  void init() {
    List<Customer> customers = initEntity("customers", CustomerDto, Customer)
    customerRepository.saveAll(customers)

    List<Car> cars = initEntity("cars", InitCarsDto, Car)
    carRepository.saveAll(cars)

    List<Order> orders = initEntity("orders", OrderDto, Order)
    orderRepository.saveAll(orders)

    List<Invoice> invoices = initEntity("invoices", InvoiceDto, Invoice)
    invoiceRepository.saveAll(invoices)

    defaultRepository.saveAll(initEntity("data", TestEntityDto, TestEntity))
  }

  void clean() {
    defaultRepository.deleteAll()
    invoiceRepository.deleteAll()
    orderRepository.deleteAll()
    carRepository.deleteAll()
    customerRepository.deleteAll()
  }

  private <D extends WithIdDto,
      E extends WithId> List<E> initEntity(String entityJson,
                                           Class<D> dto,
                                           Class<E> entity) {

    Resource entityDtos = new ClassPathResource("initdb/${entityJson}.json")
    List<D> cars = map.oMap.readValue(
        entityDtos.getInputStream(),
        map.oMap.getTypeFactory()
            .constructCollectionType(List, dto))
    map.map(cars, entity)
  }

  @EqualsAndHashCode
  @SuppressWarnings("unused")
  private static final class InitCarsDto extends CarDto {
    Integer count
  }
}

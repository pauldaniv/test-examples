package com.paul.dealer.conf

import com.paul.common.component.Mapper
import com.paul.common.payload.CarDto
import com.paul.common.payload.CustomerDto
import com.paul.common.payload.InvoiceDto
import com.paul.common.payload.OrderDto
import com.paul.common.payload.base.WithIdDto
import com.paul.dealer.domain.Car
import com.paul.dealer.domain.Customer
import com.paul.dealer.domain.Invoice
import com.paul.dealer.domain.Order
import com.paul.dealer.domain.base.WithId
import com.paul.dealer.persintence.CarRepository
import com.paul.dealer.persintence.CustomerRepository
import com.paul.dealer.persintence.InvoiceRepository
import com.paul.dealer.persintence.OrderRepository
import groovy.util.logging.Slf4j
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
@Slf4j
class AppInit {

    private final Mapper map
    private final CarRepository carRepository
    private final InvoiceRepository invoiceRepository
    private final OrderRepository orderRepository
    private final CustomerRepository customerRepository

    AppInit(Mapper map,
            CarRepository carRepository,
            InvoiceRepository invoiceRepository,
            OrderRepository orderRepository,
            CustomerRepository customerRepository) {

        this.map = map
        this.carRepository = carRepository
        this.invoiceRepository = invoiceRepository
        this.orderRepository = orderRepository
        this.customerRepository = customerRepository
    }

    @EventListener(ContextRefreshedEvent.class)
    void init() {
        log.info("Context Refreshed")

        List<Customer> customers = initEntity("customers", CustomerDto.class, Customer.class)
        customerRepository.saveAll(customers)

        List<Car> cars = initEntity("cars", CarDto.class, Car.class)
        carRepository.saveAll(cars)


        List<Invoice> invoices = initEntity("invoices", InvoiceDto.class, Invoice.class)
        invoiceRepository.saveAll(invoices)


        List<Order> orders = initEntity("orders", OrderDto.class, Order.class)
        orderRepository.saveAll(orders)




    }

    private <D extends WithIdDto,
            E extends WithId> List<E> initEntity(String entityJson,
                                                 Class<D> dto,
                                                 Class<E> entity) {

        Resource testEntityDtos = new ClassPathResource("initdb/${entityJson}.json")
        List<D> cars = map.oMap.readValue(
                testEntityDtos.getFile(),
                map.oMap.getTypeFactory()
                        .constructCollectionType(List.class, dto))
        map.map(cars, entity)
    }
}

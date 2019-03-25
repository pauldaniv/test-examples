package com.paul.dealer.jpa

import com.paul.dealer.domain.Car
import com.paul.dealer.domain.Customer
import com.paul.dealer.domain.Invoice
import com.paul.dealer.domain.Order
import com.paul.dealer.domain.TestEntity
import com.paul.dealer.persintence.*
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

import static junit.framework.TestCase.assertNotNull

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration()
class DefaultRepositoryTest {

    @Autowired
    TestEntityManager entityManager

    @Autowired
    DefaultRepository defaultRepository
    @Autowired
    CarRepository carRepository
    @Autowired
    InvoiceRepository invoiceRepository
    @Autowired
    CustomerRepository customerRepository
    @Autowired
    OrderRepository orderRepository


    @Test
    void defaultRepositoryTest() {
        def persist = entityManager.persist(TestEntity.builder().build())
        assertNotNull(defaultRepository.findById(persist.getId()))
    }

    @Test
    void carRepositoryTest() {
        def persist = entityManager.persist(Car.builder().build())
        assertNotNull(defaultRepository.findById(persist.getId()))
    }

    @Test
    void invoiceRepositoryTest() {

        def persist = entityManager.persist(Invoice.builder().build())
        assertNotNull(defaultRepository.findById(persist.getId()))
    }

    @Test
    void orderRepositoryTest() {
        def persist = entityManager.persist(Order.builder().build())
        assertNotNull(defaultRepository.findById(persist.getId()))
    }

    @Test
    void customerRepositoryTest() {
        def persist = entityManager.persist(Customer.builder().build())
        assertNotNull(defaultRepository.findById(persist.getId()))
    }

}

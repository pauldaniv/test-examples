package com.pauldaniv.dealer.integration

import com.pauldaniv.common.payload.Resp
import com.pauldaniv.common.test.groups.TestGroup
import com.pauldaniv.dealer.conf.DbInitializer
import com.pauldaniv.dealer.persistence.CarRepository
import com.pauldaniv.dealer.service.InvoiceService
import com.pauldaniv.dealer.service.OrderService
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.ObjectNotFoundException
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.security.InvalidParameterException
import javax.persistence.EntityManagerFactory

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [CreateOrderTestConfiguration::class])
@Category(TestGroup.Slow.Integration::class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
class CarPurchasingTest {

  @Autowired
  private lateinit var orderService: OrderService

  @Autowired
  private lateinit var dbInitializer: DbInitializer

  @Autowired
  private lateinit var invoiceService: InvoiceService

  @Autowired
  private lateinit var carRepository: CarRepository

  @Autowired
  private lateinit var entityManagerFactory: EntityManagerFactory

  @Before
  fun before() = dbInitializer.init()

  @After
  fun after() {
    val entityManager = entityManagerFactory.createEntityManager()
    entityManager.transaction.begin()
    entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate()
    entityManager.createNativeQuery(truncateTable("cars")).executeUpdate()
    entityManager.createNativeQuery(truncateTable("orders")).executeUpdate()
    entityManager.createNativeQuery(truncateTable("invoices")).executeUpdate()
    entityManager.createNativeQuery(truncateTable("customers")).executeUpdate()
    entityManager.createNativeQuery("truncate table invoices_orders").executeUpdate()
    entityManager.createNativeQuery("truncate table car_orders").executeUpdate()
    entityManager.transaction.commit()
  }

  @Test
  fun createOrderTest() {
    val existingIds = mutableListOf<Long>(1, 2, 3)
    val nonExistingIds = mutableListOf<Long>(4)
    val allIds = mutableListOf<Long>()
    allIds.addAll(existingIds)
    allIds.addAll(nonExistingIds)
    val createOrder = orderService.createOrder(allIds, 1)

    checkResponseIsNotNull(createOrder)
    assertThat(createOrder?.body?.body?.orderedCars?.size).isEqualTo(existingIds.size)
    assertThat(createOrder?.body?.body?.notAvailableCars?.size).isEqualTo(nonExistingIds.size)
  }

  @Test
  fun createInvoiceTest() {
    val ids = mutableListOf<Long>(1, 2, 3)
    val orderResponse = orderService.createOrder(ids, 1)
    checkResponseIsNotNull(orderResponse)

    val orderId = orderResponse?.body?.body?.order?.id
    val invoiceResponse = invoiceService.createInvoice(listOf(orderId), 1)
    checkResponseIsNotNull(invoiceResponse)
    val carsSum = carRepository.findAllById(ids).sumByDouble { it.price }
    assertThat(carsSum).isEqualTo(invoiceResponse?.body?.body?.total)
  }

  @Test(expected = InvalidParameterException::class)
  fun nullCarId() {
    orderService.createOrder(mutableListOf(1, 3, null), 1)
  }

  @Test(expected = InvalidParameterException::class)
  fun nullCustomerId() {
    orderService.createOrder(listOf(1, 3), null)
  }

  @Test(expected = ObjectNotFoundException::class)
  fun carNotFountTest() {
    orderService.createOrder(listOf(5), 1)
  }

  @Test(expected = ObjectNotFoundException::class)
  fun customerNotFountTest() {
    orderService.createOrder(listOf(4), 10)
  }

  @Test(expected = InvalidParameterException::class)
  fun invoiceServiceNullOrderId() {
    orderService.createOrder(listOf(1, 3), 1)
    invoiceService.createInvoice(listOf(null), 1)
  }

  @Test(expected = InvalidParameterException::class)
  fun invoiceServiceNullCustomerId() {
    orderService.createOrder(listOf(1, 3), 1)

    invoiceService.createInvoice(listOf(1), null)
  }

  @Test(expected = ObjectNotFoundException::class)
  fun invoiceServiceOrderNotFountTest() {
    orderService.createOrder(listOf(1, 3), 1)
    invoiceService.createInvoice(listOf(100), 1)
  }

  @Test(expected = ObjectNotFoundException::class)
  fun invoiceServiceCustomerNotFountTest() {
    orderService.createOrder(listOf(1, 3), 1)
    invoiceService.createInvoice(listOf(1), 10)
  }

  private fun <T : Resp<*>> checkResponseIsNotNull(orderResponse: ResponseEntity<T>) {
    assertThat(orderResponse).isNotNull
    assertThat(orderResponse.body).isNotNull
    assertThat(orderResponse.body?.body).isNotNull
  }

  private fun truncateTable(table: String): String {
    return "truncate table $table; ${cleanupIdentity(table)}"
  }

  // yeah, so the h2 doesn't do this automatically
  private fun cleanupIdentity(table: String): String {
    return "ALTER TABLE $table ALTER COLUMN id RESTART WITH 1"
  }
}


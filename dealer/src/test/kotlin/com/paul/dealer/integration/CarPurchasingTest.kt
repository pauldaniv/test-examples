package com.paul.dealer.integration

import com.paul.common.payload.Resp
import com.paul.common.test.groups.TestGroup
import com.paul.dealer.conf.DbInitializer
import com.paul.dealer.persistence.CarRepository
import com.paul.dealer.service.InvoiceService
import com.paul.dealer.service.OrderService
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.ObjectNotFoundException
import org.junit.Before
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.security.InvalidParameterException

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [CreateOrderTestConfiguration::class])
@Category(TestGroup.Slow.Integration::class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CarPurchasingTest {

  @Autowired
  private lateinit var orderService: OrderService

  @Autowired
  private lateinit var dbInitializer: DbInitializer

  @Autowired
  private lateinit var invoiceService: InvoiceService

  @Autowired
  private lateinit var carRepository: CarRepository

  @Before
  fun before() {
    dbInitializer.init()
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
}


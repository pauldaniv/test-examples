package com.pauldaniv.dealer.unit.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.pauldaniv.common.component.Mapper
import com.pauldaniv.common.test.groups.TestGroup
import com.pauldaniv.dealer.domain.Car
import com.pauldaniv.dealer.persistence.CarRepository
import com.pauldaniv.dealer.service.CarServiceImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.test.util.ReflectionTestUtils.setField
import java.time.YearMonth
import java.util.*


@RunWith(MockitoJUnitRunner::class)
@Category(TestGroup.Fast.Unit::class)
class CarServiceTest {

  @InjectMocks
  private lateinit var carService: CarServiceImpl

  @Spy
  private lateinit var repository: CarRepository

  private val map = Mapper(ObjectMapper())

  @Before
  fun before() {
    setField(carService, "map", map)
    setField(carService, "marginHigh", 0.1)
    setField(carService, "marginLow", 0.2)
  }

  @Test
  fun availabilityFalseTest() {
    doReturn(composeNotAvailableCar()).`when`(repository).findById(1L)
    assertThat(carService.getOne(1L).body!!.body.available).isEqualTo(false)
  }

  @Test
  fun availabilityTrueTest() {
    doReturn(composeAvailableCar()).`when`(repository).findById(1L)
    assertThat(carService.getOne(1L).body!!.body.available).isEqualTo(true)
  }

  @Test
  fun carCountTest() {
    val car = composeAvailableCar()
    doReturn(car).`when`(repository).findById(1L)
    Mockito.`when`(repository.save(car.get())).thenReturn(getCar(10).get())
    assertThat(carService.updateCarCount(1L, 10).body!!.body.count).isEqualTo(10)
  }

  private fun composeAvailableCar(): Optional<Car> {
    return getCar(1)
  }

  private fun composeNotAvailableCar(): Optional<Car> {
    return getCar(0)
  }

  private fun getCar(count: Int): Optional<Car> {
    return Optional.of(Car.builder()
        .brand("BMW")
        .model("whatever")
        .releasedIn(YearMonth.of(2017, 5))
        .price(50000.0)
        .count(count)
        .build())
  }
}

package com.paul.dealer.unit.service

import com.paul.common.component.Mapper
import com.paul.common.test.groups.TestGroup
import com.paul.dealer.domain.Car
import com.paul.dealer.persintence.CarRepository
import com.paul.dealer.service.CarServiceImpl
import org.junit.Before
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

import java.time.YearMonth

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.doReturn
import static org.springframework.test.util.ReflectionTestUtils.setField

@RunWith(MockitoJUnitRunner)
@Category([TestGroup.Fast.Unit])
class CarServiceTest {

  @InjectMocks
  CarServiceImpl carService

  @Spy
  CarRepository repository

  @Before
  void before() {
    setField(carService, 'map', new Mapper())
  }

  @Test
  void availabilityFalseTest() {
    doReturn(composeNotAvailableCar()).when(repository).findById(1L)
    assertThat(carService.getOne(1L).body.body.available).isEqualTo(false)
  }

  @Test
  void availabilityTrueTest() {
    doReturn(composeAvailableCar()).when(repository).findById(1L)
    assertThat(carService.getOne(1L).body.body.available).isEqualTo(true)
  }

  @Test
  void carCountTest() {
    def car = composeAvailableCar()
    doReturn(car).when(repository).findById(1L)
    Mockito.when(repository.save(car.get())).thenReturn(getCar(10).get())
    assertThat(carService.updateCarCount(1L, 10).body.body.count).isEqualTo(10)
  }

  Optional composeAvailableCar() {
    getCar(1)
  }

  Optional composeNotAvailableCar() {
    getCar(0)
  }

  Optional getCar(Integer count) {
    Optional.of(Car.builder()
        .brand("BMW")
        .model("whatever")
        .releasedIn(YearMonth.of(2017, 5))
        .price(50000)
        .count(count)
        .build())
  }
}

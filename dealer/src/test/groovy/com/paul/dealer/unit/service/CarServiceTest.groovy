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

  Mapper map = new Mapper()

  @Before
  void before() {
    setField(carService, 'map', map)
  }

  @Test
  void availabilityFalseTest() {
    doReturn(getCar(0)).when(repository).findById(1L)
    assertThat(carService.getOne(1L).body.body.available).isEqualTo(false)
  }

  @Test
  void availabilityTrueTest() {
    doReturn(getCar(1)).when(repository).findById(1L)
    assertThat(carService.getOne(1L).body.body.available).isEqualTo(true)
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

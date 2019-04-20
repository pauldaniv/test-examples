package com.paul.dealer.unit.service

import com.paul.common.component.Mapper
import com.paul.common.payload.CarDto
import com.paul.common.test.groups.TestGroup
import com.paul.dealer.domain.Car
import com.paul.dealer.persintence.CarRepository
import com.paul.dealer.service.CarServiceImpl
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

  @Test
  void defaultTest() {
    setField(carService, 'map', map)
    doReturn(getCar()).when(repository).findById(1L)

    assertThat(CarDto.builder()
            .brand("BMW")
            .model("whatever")
            .releasedIn(YearMonth.of(2017, 5))
            .price(50000)
            .count(300)
            .build()).isEqualToComparingFieldByField(carService.getOne(1L).body.body)
  }

  Optional getCar() {
    Optional.of(Car.builder()
        .brand("BMW")
        .model("whatever")
        .releasedIn(YearMonth.of(2017, 5))
        .price(50000)
        .count(300)
        .build())
  }
}

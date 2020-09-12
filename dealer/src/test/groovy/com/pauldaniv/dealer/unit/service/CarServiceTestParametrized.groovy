package com.pauldaniv.dealer.unit.service

import com.pauldaniv.common.component.Mapper
import com.pauldaniv.common.payload.CarDto
import com.pauldaniv.common.test.generic.DataProviderUtils
import com.pauldaniv.common.test.groups.TestGroup
import com.pauldaniv.dealer.domain.Car
import com.pauldaniv.dealer.persistence.CarRepository
import com.pauldaniv.dealer.service.CarServiceImpl
import org.junit.Before
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.InjectMocks
import org.mockito.Spy

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.doReturn
import static org.mockito.MockitoAnnotations.initMocks
import static org.springframework.test.util.ReflectionTestUtils.setField

@RunWith(Parameterized)
@Category(TestGroup.Fast.Component)
class CarServiceTestParametrized {

  @InjectMocks
  CarServiceImpl carService

  @Spy
  CarRepository repository

  CarDto dto

  CarServiceTestParametrized(CarDto dto) {
    this.dto = dto
  }

  def map = new Mapper()

  @Before
  void before() {
    initMocks(this)
    setField(carService, 'map', map)
    setField(carService, 'marginHigh', 0.1D)
    setField(carService, 'marginLow', 0.2D)
  }

  @Test
  void checkPriceTest() {
    def marginHigh = 0.1D
    def marginLow = 0.2D

    def currentPrice = dto.price

    if (dto.count > 100) {
      currentPrice += dto.price * marginHigh
    } else if (dto.count < 10) {
      currentPrice += dto.price * marginLow
    }

    doReturn(Optional.of(map.map(dto, Car))).when(repository).findById(1L)
    assertThat(carService.getOne(1L).body.body.price).isEqualTo(currentPrice)
  }

  @Parameterized.Parameters
  static Collection<Object[]> getData() {
    DataProviderUtils.getData("cars", CarDto)
  }
}

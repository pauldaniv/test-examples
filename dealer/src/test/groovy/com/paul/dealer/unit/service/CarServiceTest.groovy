package com.paul.dealer.unit.service

import com.paul.common.component.Mapper
import com.paul.common.payload.CarDto
import com.paul.common.test.generic.DataProviderUtils
import com.paul.common.test.groups.TestGroup
import com.paul.dealer.domain.Car
import com.paul.dealer.persintence.CarRepository
import com.paul.dealer.service.CarServiceImpl
import org.junit.Before
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Spy

import java.time.YearMonth

import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.doReturn
import static org.mockito.MockitoAnnotations.initMocks
import static org.springframework.test.util.ReflectionTestUtils.setField

@RunWith(Parameterized)
@Category([TestGroup.Fast.Unit])
class CarServiceTest {

  @InjectMocks
  CarServiceImpl carService

  @Spy
  CarRepository repository

  CarDto dto

  CarServiceTest(CarDto dto) {
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
  void testCheckPriceTest() {
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

  @Parameterized.Parameters
  static Collection<Object[]> getData() {
    DataProviderUtils.getData("cars", CarDto)
  }
}

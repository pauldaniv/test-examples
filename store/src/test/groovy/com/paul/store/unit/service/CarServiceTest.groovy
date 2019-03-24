package com.paul.store.unit.service

import com.paul.common.client.dealer.CarClient
import com.paul.common.payload.CarDto
import com.paul.common.payload.Resp
import com.paul.common.test.groups.Fast
import com.paul.store.service.CarStoreServiceImpl
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.test.util.ReflectionTestUtils

import static org.assertj.core.api.Assertions.assertThat

@RunWith(MockitoJUnitRunner.class)
@Category([Fast.Unit.class])
class CarServiceTest {

  @InjectMocks
  CarStoreServiceImpl carStoreService

  @Spy
  CarClient carClient

  @Test
  void tradeMarginTest() {
    ReflectionTestUtils.setField(carStoreService, 'margin', 10)

    Mockito.doReturn(Resp.ok(CarDto.builder()
            .price(1000d).build()
    )).when(carClient).info(1L)

    def that = assertThat(Resp.ok(CarDto.builder()
            .price(1100d).build()).body.body)
    def info = carStoreService.getCarInfo(1L)
    that.isEqualToComparingFieldByField(info.body.body)
  }
}

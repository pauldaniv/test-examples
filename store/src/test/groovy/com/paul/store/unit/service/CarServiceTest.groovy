package com.paul.store.unit.service

import com.paul.common.client.dealer.CarClient
import com.paul.common.payload.CarDto
import com.paul.common.payload.Resp
import com.paul.store.service.CarStoreServiceImpl
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

import static org.assertj.core.api.Assertions.assertThat

@RunWith(MockitoJUnitRunner.class)
class CarServiceTest {

    @InjectMocks
    CarStoreServiceImpl carStoreService

    @Spy
    CarClient carClient

    @Test
    void tradeMarginTest() {
        Mockito.doReturn(Resp.ok(CarDto.builder()
                .price(1000D).build()
        )).when(carClient).info(1L)

        def that = assertThat(Resp.ok(CarDto.builder()
                .price(1100D).build()))
        def info = carStoreService.getCarInfo(1L)
        that.isEqualToComparingOnlyGivenFields(info, 'price')
    }
}

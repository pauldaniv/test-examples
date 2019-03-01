package com.paul.store.unit.controller

import com.paul.common.payload.TestEntityDto
import com.paul.store.controller.StoreController
import com.paul.store.service.DealerService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

import static com.paul.common.payload.Resp.ok
import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.doReturn

@RunWith(MockitoJUnitRunner.class)
class TestControllerTest {

  @InjectMocks
  StoreController storeController

  @Spy
  DealerService dealerService

  @Test
  void simpleControllerTest() {

    doReturn(
            ok(TestEntityDto.builder()
                    .firstName("testName")
                    .lastName("testLastName")
                    .build())).when(dealerService).getOne(1L)

    assertThat(
            ok(TestEntityDto.builder()
                    .firstName("testName")
                    .lastName("testLastName")
                    .build())).isEqualToComparingFieldByFieldRecursively(storeController.getOne(1L))
  }
}

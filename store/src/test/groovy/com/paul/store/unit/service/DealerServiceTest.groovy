package com.paul.store.unit.service

import com.paul.common.test.groups.TestGroup
import com.paul.store.client.DealerClient
import com.paul.store.service.DealerServiceImpl
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

import static com.paul.common.payload.Resp.ok
import static com.paul.common.payload.TestEntityDto.builder
import static org.assertj.core.api.Assertions.assertThat
import static org.mockito.Mockito.doReturn

@RunWith(MockitoJUnitRunner)
@Category([TestGroup.Fast.Unit])
class DealerServiceTest {

  @InjectMocks
  DealerServiceImpl dealerService

  @Spy
  DealerClient dealerClient

  @Test
  void testGetUserDetails() {

    doReturn(
        ok(builder()
            .firstName("testName")
            .lastName("testLastName")
            .build())).when(dealerClient).getOne(1L)

    assertThat(
        ok(builder()
            .firstName("TESTNAME")
            .lastName("testLastName")
            .build())).isEqualToComparingFieldByField(dealerService.getOne(1L))
  }
}

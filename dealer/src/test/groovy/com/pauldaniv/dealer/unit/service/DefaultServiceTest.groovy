package com.pauldaniv.dealer.unit.service

import com.pauldaniv.common.component.Mapper
import com.pauldaniv.common.test.groups.TestGroup
import com.pauldaniv.dealer.domain.TestEntity
import com.pauldaniv.dealer.persistence.DefaultRepository
import com.pauldaniv.dealer.service.DefaultServiceImpl
import org.junit.Before
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

import static org.assertj.core.api.Assertions.assertThat
import static org.springframework.test.util.ReflectionTestUtils.setField

@RunWith(MockitoJUnitRunner)
@Category([TestGroup.Fast.Unit])
class DefaultServiceTest {

  @InjectMocks
  DefaultServiceImpl defaultService

  @Spy
  DefaultRepository repository

  @Before
  void before() {
    setField(defaultService, 'map', new Mapper())
  }

  @Test
  void saveTest() {

    Mockito.doReturn(Optional.of(TestEntity.builder()
        .firstName("John")
        .lastName("Doh")
        .build())).when(repository).findById(1L)

    def list = defaultService.getOne(1L).body.body.firstName.getChars().toList()
    assertThat(list.stream().allMatch(Character::isUpperCase)).isTrue()
  }
}

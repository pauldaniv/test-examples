package com.paul.dealer.unit.service

import com.paul.common.component.Mapper
import com.paul.dealer.domain.TestEntity
import com.paul.dealer.persintence.DefaultRepository
import com.paul.dealer.service.DefaultService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

import static org.assertj.core.api.Assertions.assertThat
import static org.springframework.test.util.ReflectionTestUtils.setField

@RunWith(MockitoJUnitRunner.class)
class DefaultServiceTest {

  @InjectMocks
  DefaultService defaultService

  @Spy
  DefaultRepository repository

  @Test
  void saveTest() {
    setField(defaultService, 'map', new Mapper())

    Mockito.doReturn(Optional.of(TestEntity.builder()
            .firstName("John")
            .lastName("Doh")
            .build())).when(repository).findById(1L)


    def list = defaultService.getOne(1L).body.body.firstName.getChars().toList()
    assertThat(list.stream().allMatch(Character::isUpperCase)).isTrue()
  }
}

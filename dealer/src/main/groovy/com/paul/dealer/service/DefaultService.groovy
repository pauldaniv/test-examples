package com.paul.dealer.service


import com.paul.common.payload.TestEntityDto
import com.paul.dealer.domain.TestEntity
import com.paul.dealer.persintence.DefaultRepository
import groovy.transform.TypeChecked
import org.springframework.stereotype.Service

@Service
@TypeChecked
class DefaultService extends AbstractCommonService<TestEntityDto, TestEntity, DefaultRepository> {
  DefaultService(DefaultRepository repository) {
    super(repository)
  }
}

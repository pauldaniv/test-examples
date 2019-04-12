package com.paul.dealer.service

import com.paul.common.component.Mapper
import com.paul.common.payload.Resp
import com.paul.common.payload.TestEntityDto
import com.paul.dealer.domain.TestEntity
import com.paul.dealer.persintence.DefaultRepository
import org.hibernate.ObjectNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class DefaultServiceImpl extends AbstractCommonService<TestEntityDto, TestEntity, DefaultRepository> implements DefaultService{

  private DefaultRepository repository
  private Mapper map

  DefaultServiceImpl(DefaultRepository repository, Mapper map) {
    super(repository)
    this.repository = repository
    this.map = map
  }

  @Override
  ResponseEntity getOne(Long id) {
    def one = repository.findById(id).orElseThrow({ new ObjectNotFoundException(id, entityType.simpleName) })
    one.firstName = one.firstName.toUpperCase()
    Resp.ok(map.map(one, dtoType))
  }
}
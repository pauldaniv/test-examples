package com.pauldaniv.dealer.service

import com.pauldaniv.common.component.Mapper
import com.pauldaniv.common.payload.Resp
import com.pauldaniv.common.payload.TestEntityDto
import com.pauldaniv.dealer.domain.TestEntity
import com.pauldaniv.dealer.persistence.DefaultRepository
import org.hibernate.ObjectNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class DefaultServiceImpl extends AbstractCommonService<TestEntityDto, TestEntity, DefaultRepository> implements DefaultService {

  private DefaultRepository repository
  private Mapper map

  DefaultServiceImpl(DefaultRepository repository, Mapper map) {
    super(repository)
    this.repository = repository
    this.map = map
  }

  @Override
  ResponseEntity getOne(Long id) {
    def one = repository.findById(id).orElseThrow({
      new ObjectNotFoundException(id, "DefaultEntity")
    })
    one.firstName = one.firstName.toUpperCase()
    Resp.ok(map.map(one, dtoType))
  }
}

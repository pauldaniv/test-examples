package com.paul.dealer.service

import com.paul.dealer.persintence.DefaultRepository
import com.paul.library.domain.TestEntity
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class DefaultServiceImpl implements DefaultService {

  private final DefaultRepository repository

  TestEntity getOne(String id) {
    return repository.findById(id).get()
  }

  Iterable<TestEntity> getAll() {
    return repository.findAll()
  }

  String save(TestEntity entity) {
    repository.save(entity)
    return "ok"
  }
}

package com.paul.dealer.service;


import com.paul.dealer.persintence.DefaultRepository;
import com.paul.library.domain.TestEntity;
import org.springframework.stereotype.Service;

@Service
public class DefaultServiceImpl implements DefaultService {

  private final DefaultRepository repository;

  public DefaultServiceImpl(DefaultRepository repository) {
    this.repository = repository;
  }

  public TestEntity getOne(String id) {
    return repository.findById(id).get();
  }

  public Iterable<TestEntity> getAll() {
    return repository.findAll();
  }

  public String save (TestEntity entity) {
    repository.save(entity);
    return "ok";
  }
}

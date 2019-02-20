package com.paul.dealer.service;


import com.paul.library.domen.TestEntity;
import com.paul.dealer.persintence.DefaultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultService {
  private final DefaultRepository repository;

  public DefaultService(DefaultRepository repository) {
    this.repository = repository;
  }

  public TestEntity getOne(String id) {
    return repository.findById(id).get();
  }

  public List<TestEntity> getAll() {
    return repository.findAll();
  }

  public String save (TestEntity entity) {
    repository.save(entity);
    return "ok";
  }
}

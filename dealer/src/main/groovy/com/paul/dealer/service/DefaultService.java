package com.paul.dealer.service;

import com.paul.library.domain.TestEntity;

public interface DefaultService {

    TestEntity getOne(String id);

    Iterable<TestEntity> getAll();

    String save(TestEntity entity);

}

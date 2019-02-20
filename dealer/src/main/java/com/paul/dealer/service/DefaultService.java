package com.paul.dealer.service;

import com.paul.library.domen.TestEntity;

public interface DefaultService {

    TestEntity getOne(String id);

    Iterable<TestEntity> getAll();

    String save(TestEntity entity);

}

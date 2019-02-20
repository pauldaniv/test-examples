package com.paul.dealer.persintence;

import com.paul.library.domen.TestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DefaultRepository extends MongoRepository<TestEntity, String> {
}

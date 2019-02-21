package com.paul.dealer.persintence;

import com.paul.library.domain.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface DefaultRepository extends CrudRepository<TestEntity, String> {
}

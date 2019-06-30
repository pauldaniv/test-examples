package com.paul.dbservice.persistence;

import com.paul.dbservice.domain.base.WithId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonRepository<E extends WithId<E>> extends MongoRepository<E, String> {
}

package com.pauldaniv.dealer.persistence;

import com.pauldaniv.dealer.domain.base.WithId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
interface CommonRepository<E extends WithId<E>> extends CrudRepository<E, Long> {
}

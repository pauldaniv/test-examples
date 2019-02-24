package com.paul.dealer.persintence;

import com.paul.dealer.domain.base.WithId;
import org.springframework.data.repository.CrudRepository;

public interface CommonRepository<E extends WithId<E>> extends CrudRepository<E, Long> {

}

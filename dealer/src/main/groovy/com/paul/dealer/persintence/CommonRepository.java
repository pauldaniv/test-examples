package com.paul.dealer.persintence;

import com.paul.dealer.domain.base.WithId;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

@NoRepositoryBean
public interface CommonRepository<E extends WithId<E>> extends CrudRepository<E, Long> {

    default List<E> findAll(List<Long> ids) {
        return ids.parallelStream().map(this::findOne).collect(Collectors.toList());
    }

    default E findOne(final Long id) {
        return findById(id).orElseThrow(() -> new ObjectNotFoundException(id, getEntityType().getSimpleName()));
    }

    @SuppressWarnings("unchecked")
    private Class<E> getEntityType() {
        return ((Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }
}

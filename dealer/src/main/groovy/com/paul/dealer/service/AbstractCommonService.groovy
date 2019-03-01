package com.paul.dealer.service

import com.paul.common.component.Mapper
import com.paul.common.payload.Resp
import com.paul.common.payload.base.WithIdDto
import com.paul.dealer.domain.base.WithId
import com.paul.dealer.persintence.CommonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

import static org.springframework.http.HttpStatus.OK

@Service
abstract class AbstractCommonService<
        D extends WithIdDto,
        E extends WithId<E>,
        R extends CommonRepository<E>> implements CommonService<D> {

  @Autowired
  private Mapper map

  private R repository
  private Class<D> d
  private Class<E> e

  AbstractCommonService(R repository) {
    initClassTypes()
    this.repository = repository
  }

  @Override
  ResponseEntity getOne(Long id) {
    Optional<E> one = repository.findById(id)
    if (one.isPresent()) {
      Resp.ok(map.map(one.get(), d))
    } else {
      Resp.fail("Object not found", OK)
    }
  }

  @Override
  ResponseEntity getAll() {
    Resp.ok(map.map(repository.findAll(), d))
  }

  @Override
  ResponseEntity save(D dto) {
    E save = repository.save(map.map(dto, e))

    D map = map.map(save, d)
    Resp.ok(map)
  }

  ResponseEntity update(D dto) {
    def entity = repository.findById(dto.id)
    if (entity.isPresent()) {
      Resp.ok(map.map(repository.save(map.map(dto, e)), d))
    } else {
      Resp.ok("Object not found", false)
    }
  }

  private void initClassTypes() {
    d = (Class<D>) getTypeArgument(0)
    e = (Class<E>) getTypeArgument(1)
  }

  private Type getTypeArgument(int index) {
    ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[index]
  }
}

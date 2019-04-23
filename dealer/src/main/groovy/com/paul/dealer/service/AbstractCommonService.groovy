package com.paul.dealer.service

import com.paul.common.component.Mapper
import com.paul.common.payload.Resp
import com.paul.common.payload.base.WithIdDto
import com.paul.dealer.domain.base.WithId
import com.paul.dealer.persintence.CommonRepository
import org.hibernate.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@Service
abstract class AbstractCommonService<
        D extends WithIdDto,
        E extends WithId<E>,
        R extends CommonRepository<E>> implements CommonService<D> {

  private static final int DTO_TYPE = 0, ENTITY_TYPE = 1

  @Autowired
  private Mapper map

  private R repository
  private Class<D> d
  private Class<E> e

  AbstractCommonService() {}

  AbstractCommonService(R repository) {
    initClassTypes()
    this.repository = repository
  }

  @Override
  ResponseEntity getOne(Long id) {
    def one = repository.findById(id).orElseThrow({ new ObjectNotFoundException(id, entityType.simpleName) })
    Resp.ok(map.map(one, d))
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

  @Override
  ResponseEntity update(D dto) {
    def entity = repository.findById(dto.id)
    if (entity.present) {
      def mappedEntity = map.map(dto, e)
      Resp.ok(map.map(repository.save(mappedEntity), d))
    } else {
      throw new ObjectNotFoundException(dto.getId(), e.simpleName)
    }
  }

  Class<D> getDtoType() { d }

  Class<E> getEntityType() { e }

  private void initClassTypes() {
    d = (Class<D>) getTypeArgument(DTO_TYPE)
    e = (Class<E>) getTypeArgument(ENTITY_TYPE)
  }

  private Type getTypeArgument(int index) {
    ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[index]
  }
}

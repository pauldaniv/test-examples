package com.paul.dealer.service

import com.paul.common.component.Mapper
import com.paul.common.payload.Resp
import com.paul.common.payload.base.WithIdDto
import com.paul.dealer.domain.base.WithId
import com.paul.dealer.persistence.CommonRepository
import org.hibernate.ObjectNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.security.InvalidParameterException
import java.util.stream.Collectors

@Service
abstract class AbstractCommonService<
    D extends WithIdDto,
    E extends WithId<E>,
    R extends CommonRepository<E>> implements CommonService<D, E> {

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
    if (dto.id == null) {
      throw new InvalidParameterException("Invalid entity id")
    }
    saveEntity(repository.findById(dto.id).orElseThrow(() -> new ObjectNotFoundException(dto.id, entityType.simpleName)))
  }

  @Override
  ResponseEntity<Resp<D>> saveEntity(E entity) {
    return ok(repository.save(entity))
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


  ResponseEntity<Resp<D>> ok(E dto) { Resp.ok(map(dto)) }

  List<D> map(List<E> entities) {
    entities.stream().map { map(it) } .collect(Collectors.toList())
  }

  D map(E entity) { map.map(entity, d) }
}

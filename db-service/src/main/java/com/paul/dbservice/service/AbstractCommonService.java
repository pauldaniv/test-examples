package com.paul.dbservice.service;

import com.paul.common.component.Mapper;
import com.paul.common.payload.Resp;
import com.paul.common.payload.base.WithIdDto;
import com.paul.dbservice.domain.base.WithId;
import com.paul.dbservice.persistence.CommonRepository;
import org.bson.internal.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
abstract class AbstractCommonService<
        D extends WithIdDto,
        E extends WithId<E>,
        R extends CommonRepository<E>> implements CommonService<D, E> {

    private static final int DTO_TYPE = 0, ENTITY_TYPE = 1;

    @Autowired
    private Mapper map;

    private R repository;
    private Class<D> d;
    private Class<E> e;

    AbstractCommonService() {
    }

    AbstractCommonService(R repository) {
        initClassTypes();
        this.repository = repository;
    }

    @Override
    public ResponseEntity<Resp<D>> getOne(String id) {
        E one = repository.findById(id).orElseThrow();
        return Resp.ok(map.map(one, d));
    }

    @Override
    public ResponseEntity<Resp<List<D>>> getAll() {
        return Resp.ok(map.map(repository.findAll(), d));
    }

    @Override
    public ResponseEntity save(D dto) {
        final E entity = map.map(dto, e);
        entity.setId(Base64.encode(LocalDateTime.now().toString().getBytes()));
        final E save = repository.save(entity);
        final D map = this.map.map(save, d);
        return Resp.ok(map);
    }

    @Override
    public ResponseEntity update(D dto) {
        if (dto.getId() == null) {
            throw new InvalidParameterException("Invalid entity id");
        }
        return saveEntity(repository.findById(dto.getId()).orElseThrow());
    }

    @Override
    public ResponseEntity<Resp<D>> saveEntity(E entity) {
        return ok(repository.save(entity));
    }

    Class<D> getDtoType() {
        return d;
    }

    Class<E> getEntityType() {
        return e;
    }

    @SuppressWarnings("unchecked")
    private void initClassTypes() {
        d = (Class<D>) getTypeArgument(DTO_TYPE);
        e = (Class<E>) getTypeArgument(ENTITY_TYPE);
    }

    private Type getTypeArgument(int index) {
        return ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[index];
    }


    private ResponseEntity<Resp<D>> ok(E dto) {
        return Resp.ok(map(dto));
    }

    List<D> map(List<E> entities) {
        return entities.stream().map(this::map).collect(Collectors.toList());
    }

    private D map(E entity) {
        return map.map(entity, d);
    }
}

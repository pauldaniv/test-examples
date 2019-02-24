package com.paul.dealer.service;

import com.paul.dealer.domain.base.WithId;
import com.paul.dealer.persintence.CommonRepository;
import com.paul.library.component.Mapper;
import com.paul.library.payload.Resp;
import com.paul.library.payload.base.WithIdDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;

@Service
public abstract class AbstractCommonService<
        D extends WithIdDto,
        E extends WithId<E>,
        R extends CommonRepository<E>> implements CommonService<D> {

    @Autowired
    private Mapper mapper;

    private R repository;
    private Class<D> dto;
    private Class<E> entity;

    public AbstractCommonService(R repository) {
        initClassTypes();
        this.repository = repository;
    }

    @SuppressWarnings("unchecked")
    private void initClassTypes() {
        dto = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        entity = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @Override
    public ResponseEntity<Resp<D>> getOne(Long id) {
        D map = mapper.map(mapper.map(repository.findById(id).get(), dto), this.dto);
        return Resp.ok(map);
    }

    @Override
    public Iterable<D> getAll() {
        return mapper.map(repository.findAll(), dto);
    }

    @Override
    public ResponseEntity<Resp<D>> save(D dto) {
        E save = repository.save(mapper.map(dto, entity));
        D map = mapper.map(save, this.dto);
        ResponseEntity<Resp<D>> ok = Resp.ok(map);
        return ok;
    }
}

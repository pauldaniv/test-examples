package com.paul.dbservice.service;

import com.paul.common.payload.Resp;
import com.paul.common.payload.base.WithIdDto;
import com.paul.dbservice.domain.base.WithId;
import org.springframework.http.ResponseEntity;

import java.util.List;

interface CommonService<D extends WithIdDto, E extends WithId> {
    ResponseEntity<Resp<D>> getOne(String id);

    ResponseEntity<Resp<List<D>>> getAll();

    ResponseEntity save(D dto);

    ResponseEntity update(D dto);

    ResponseEntity saveEntity(E entity);
}

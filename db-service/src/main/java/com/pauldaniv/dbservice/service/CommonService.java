package com.pauldaniv.dbservice.service;

import com.pauldaniv.common.payload.Resp;
import com.pauldaniv.dbservice.domain.base.WithId;
import com.pauldaniv.dbservice.domain.dto.base.WithIdDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

interface CommonService<D extends WithIdDto, E extends WithId> {
    ResponseEntity<Resp<D>> getOne(String id);

    ResponseEntity<Resp<List<D>>> getAll();

    ResponseEntity save(D dto);

    ResponseEntity update(D dto);

    ResponseEntity saveEntity(E entity);
}

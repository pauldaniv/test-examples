package com.paul.dealer.service;

import com.paul.library.payload.base.WithIdDto;
import org.springframework.http.ResponseEntity;

public interface CommonService< D extends WithIdDto> {
    ResponseEntity getOne(Long id);
    Iterable<D> getAll();
    ResponseEntity save(D entity);
}

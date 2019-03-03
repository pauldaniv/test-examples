package com.paul.dealer.service;

import com.paul.common.payload.base.WithIdDto;
import org.springframework.http.ResponseEntity;

public interface CommonService<D extends WithIdDto> {
  ResponseEntity getOne(Long id);

  ResponseEntity getAll();

  ResponseEntity save(D entity);
  ResponseEntity update(D entity);
}
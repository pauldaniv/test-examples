package com.paul.dealer.service

import com.paul.common.payload.base.WithIdDto
import com.paul.dealer.domain.base.WithId
import org.springframework.http.ResponseEntity

interface CommonService<D extends WithIdDto, E extends WithId> {
  ResponseEntity getOne(Long id)

  ResponseEntity getAll()

  ResponseEntity save(D dto)

  ResponseEntity update(D dto)

  ResponseEntity saveEntity(E entity)
}

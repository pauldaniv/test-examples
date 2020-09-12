package com.pauldaniv.dealer.service

import com.pauldaniv.common.payload.base.WithIdDto
import com.pauldaniv.dealer.domain.base.WithId
import org.springframework.http.ResponseEntity

interface CommonService<D extends WithIdDto, E extends WithId> {
  ResponseEntity getOne(Long id)

  ResponseEntity getAll()

  ResponseEntity save(D dto)

  ResponseEntity update(D dto)

  ResponseEntity saveEntity(E entity)
}

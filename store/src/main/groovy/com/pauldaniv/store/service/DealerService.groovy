package com.pauldaniv.store.service

import com.pauldaniv.common.payload.Resp
import com.pauldaniv.common.payload.TestEntityDto
import org.springframework.http.ResponseEntity

interface DealerService {
  ResponseEntity<Resp<TestEntityDto>> getOne(Long id)

  ResponseEntity<Resp<List<TestEntityDto>>> getAll()

  ResponseEntity<Resp<TestEntityDto>> update(TestEntityDto dto)

  ResponseEntity<Resp> save(TestEntityDto dto)

  ResponseEntity<Resp> index()
}

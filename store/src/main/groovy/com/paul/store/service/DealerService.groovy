package com.paul.store.service

import com.paul.common.payload.Resp
import com.paul.common.payload.TestEntityDto
import org.springframework.http.ResponseEntity

interface DealerService {
  ResponseEntity<Resp<TestEntityDto>> getOne(Long id)

  ResponseEntity<Resp<List<TestEntityDto>>> getAll()

  ResponseEntity<Resp<TestEntityDto>> update(TestEntityDto dto)

  ResponseEntity<Resp> save(TestEntityDto dto)

  ResponseEntity<Resp> index()
}

package com.paul.store.service

import com.paul.library.client.dealer.DealerClient
import com.paul.library.payload.Resp
import com.paul.library.payload.TestEntityDto
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class DealerService {

  private final DealerClient dealerTestClient

  DealerService(DealerClient client) {
    this.dealerTestClient = client
  }

  ResponseEntity<Resp<TestEntityDto>> getOne(Long id) {
    dealerTestClient.getOne(id)
  }

  ResponseEntity<Resp<List<TestEntityDto>>> getAll() {
    dealerTestClient.getAll()
  }

  ResponseEntity<Resp<TestEntityDto>> update(TestEntityDto dto) {
    dealerTestClient.update(dto)
  }

  ResponseEntity save(TestEntityDto dto) {
    dealerTestClient.save(dto)
  }

  ResponseEntity<Resp> index() {
    dealerTestClient.healthCheck()
  }
}

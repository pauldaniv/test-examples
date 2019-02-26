package com.paul.store.service

import com.paul.common.client.dealer.DealerClient
import com.paul.common.payload.Resp
import com.paul.common.payload.TestEntityDto
import groovy.util.logging.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
@Slf4j
class DealerService {

  private final DealerClient dealerTestClient

  DealerService(DealerClient client) {
    this.dealerTestClient = client
  }

  ResponseEntity<Resp<TestEntityDto>> getOne(Long id) {
    log.debug('Get one {}', id)
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

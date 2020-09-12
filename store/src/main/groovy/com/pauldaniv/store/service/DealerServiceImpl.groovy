package com.pauldaniv.store.service

import com.pauldaniv.common.payload.Resp
import com.pauldaniv.common.payload.TestEntityDto
import com.pauldaniv.store.client.DealerClient
import groovy.util.logging.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
@Slf4j
class DealerServiceImpl implements DealerService {

  private final DealerClient dealerTestClient

  DealerServiceImpl(DealerClient client) {
    this.dealerTestClient = client
  }

  ResponseEntity<Resp<TestEntityDto>> getOne(final Long id) {
    log.debug('Get one {}', id)
    def one = dealerTestClient.getOne(id)
    if (one.body.success) {
      one.body.body.firstName = one.body.body.firstName.toUpperCase()
      one
    } else {
      Resp.ok(one.body.message, one.body.success)
    }
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

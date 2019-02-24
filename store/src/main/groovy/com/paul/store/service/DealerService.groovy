package com.paul.store.service

import com.paul.library.client.DealerClient
import com.paul.library.payload.Resp
import com.paul.library.payload.TestEntityDto
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class DealerService {

  private final DealerClient client

  DealerService(DealerClient client) {
    this.client = client
  }

  ResponseEntity<Resp<TestEntityDto>> getOne(Long id) {
    def one = client.getOne(id)
    return one;
  }

  List<TestEntityDto> getAll() {
    return client.getAll();
  }

  ResponseEntity save(TestEntityDto entity) {
    return client.save(entity);
  }

  String index() {
    return client.index();
  }
}

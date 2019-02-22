package com.paul.store;

import com.paul.library.client.DealerClient;
import com.paul.library.domain.TestEntity
import org.springframework.stereotype.Service;

@Service
class DealerService {

  private final DealerClient client;

  DealerService(DealerClient client) {
    this.client = client
  }

  TestEntity getOne(Long id) {
    return client.getOne(id);
  }

  List<TestEntity> getAll() {
    return client.getAll();
  }

  String save(TestEntity entity) {
    return client.save(entity);
  }

  String index() {
    return client.index();
  }
}

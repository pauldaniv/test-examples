package com.paul.store.application

import com.paul.common.test.groups.TestGroup
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner)
@SpringBootTest
@Category([TestGroup.Slow.Application])
class GenericStoreApplicationTest {

  @Autowired
  ApplicationContext context

  @Test
  void defaultTest() {
    Assertions.assertThat(context).isNotNull()
  }
}

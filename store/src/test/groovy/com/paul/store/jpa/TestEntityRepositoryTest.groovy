package com.paul.store.jpa

import com.paul.common.payload.TestEntityDto
import com.paul.common.test.generic.AbstractTest
import com.paul.common.test.groups.TestGroup
import com.tngtech.java.junit.dataprovider.DataProvider
import com.tngtech.java.junit.dataprovider.DataProviderRunner
import com.tngtech.java.junit.dataprovider.UseDataProvider
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@ActiveProfiles("test")
@RunWith(DataProviderRunner.class)
@DataJpaTest
@ContextConfiguration()
@Category([TestGroup.Slow.Application])
class TestEntityRepositoryTest {


  @Test
  @UseDataProvider("getData")
  void test(TestEntityDto dto) {
    println dto
    println new URL("https://www.google.com").getText()
  }

  @DataProvider
  static Object[][] getData() throws FileNotFoundException {
    AbstractTest.getData("test")
  }
}

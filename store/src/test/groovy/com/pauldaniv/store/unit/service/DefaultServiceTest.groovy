package com.pauldaniv.store.unit.service

import com.pauldaniv.common.payload.TestEntityDto
import com.pauldaniv.common.test.generic.DataProviderUtils
import com.pauldaniv.common.test.groups.TestGroup
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
@RunWith(DataProviderRunner)
@DataJpaTest
@ContextConfiguration()
@Category([TestGroup.Slow.Application])
class DefaultServiceTest {


  @Test
  @UseDataProvider("getData")
  void test(TestEntityDto dto) {
    println dto
    println new URL("https://www.google.com").getText()
  }

  @DataProvider
  static List<TestEntityDto> getData() throws FileNotFoundException {
    DataProviderUtils.getData("test", TestEntityDto)
  }
}

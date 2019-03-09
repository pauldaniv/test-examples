package com.paul.store.integration

import com.paul.common.payload.TestEntityDto
import com.paul.common.test.groups.Slow
import com.paul.store.generic.AbstractTest
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner
import org.testng.annotations.Test

@RunWith(SpringRunner.class)
class DefaultServiceTest extends AbstractTest {


    @Test(dataProvider = "getData", groups = Slow.Integration.name)
    void test(TestEntityDto dto) {
        println dto
        println new URL("https://www.google.com").getText()
    }

    @Override
    String getFileName() { "test" }
}

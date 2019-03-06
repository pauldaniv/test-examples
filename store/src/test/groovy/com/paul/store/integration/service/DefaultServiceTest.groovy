package com.paul.store.integration.service

import com.paul.common.payload.TestEntityDto
import com.paul.store.generic.AbstractTest
import org.testng.annotations.Test

class DefaultServiceTest extends AbstractTest {


    @Test
    void anotherTest() {
        println "TEST!!!1"
        println "TEST!!!1"
        println "TEST!!!1"
        println "TEST!!!1"
        println "TEST!!!1"
        println "TEST!!!1"
    }

    @Test(dataProvider = "getData")
    void test(TestEntityDto dto) {
        println dto
        println new URL("https://www.google.com").getText()
    }

    @Override
    String getFileName() { "test" }
}

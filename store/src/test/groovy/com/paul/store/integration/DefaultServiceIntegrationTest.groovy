package com.paul.store.integration

import com.paul.common.test.groups.TestGroup
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner)
@SpringBootTest
@AutoConfigureMockMvc
@Category([TestGroup.Slow.API])
class DefaultServiceIntegrationTest {
    @Autowired
    private MockMvc mvc

    @Test
    void exampleTest() throws Exception {
        this.mvc.perform(get("/healthCheck")).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")))
    }
}

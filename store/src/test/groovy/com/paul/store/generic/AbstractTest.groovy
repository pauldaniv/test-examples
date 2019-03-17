package com.paul.store.generic

import com.fasterxml.jackson.databind.ObjectMapper
import com.paul.common.component.Mapper
import com.paul.common.payload.TestEntityDto
import com.paul.common.payload.base.WithIdDto
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

@Test
abstract class AbstractTest {

  private final Mapper map = new Mapper(new ObjectMapper())

  @DataProvider
  Object[][] getData() throws FileNotFoundException {
    def dtos = initEntity(getFileName(), TestEntityDto.class)
    Object[][] returnValue = new Object[dtos.size()][1]
    int index = 0
    for (Object[] each : returnValue) {
      each[0] = dtos.get(index++)
    }
    return returnValue
  }


  private <D extends WithIdDto> List<D> initEntity(String entityJson,
                                                   Class<D> dto) {

    Resource entityDtos = new ClassPathResource("${entityJson}.json")
    return map.oMap.readValue(
            entityDtos.getFile(),
            map.oMap.getTypeFactory()
                    .constructCollectionType(List.class, dto))
  }

  abstract String getFileName()
}

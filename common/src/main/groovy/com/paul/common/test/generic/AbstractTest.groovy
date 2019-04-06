package com.paul.common.test.generic

import com.fasterxml.jackson.databind.ObjectMapper
import com.paul.common.component.Mapper
import com.paul.common.payload.TestEntityDto
import com.paul.common.payload.base.WithIdDto
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource

abstract class AbstractTest {

  private static final Mapper map = new Mapper(new ObjectMapper())

  static Object[][] getData(String fileName) throws FileNotFoundException {
    def dtos = initEntity(fileName, TestEntityDto.class)
    Object[][] returnValue = new Object[dtos.size()][1]
    int index = 0
    for (Object[] each : returnValue) {
      each[0] = dtos.get(index++)
    }
    return returnValue
  }


  private static <D extends WithIdDto> List<D> initEntity(String entityJson,
                                                          Class<D> dto) {

    Resource entityDtos = new ClassPathResource("${entityJson}.json")
    return map.oMap.readValue(
            entityDtos.getFile(),
            map.oMap.getTypeFactory()
                    .constructCollectionType(List.class, dto))
  }

  abstract String getFileName()
}

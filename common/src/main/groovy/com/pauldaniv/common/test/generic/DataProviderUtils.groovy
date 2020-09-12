package com.pauldaniv.common.test.generic

import com.fasterxml.jackson.databind.ObjectMapper
import com.pauldaniv.common.component.Mapper
import com.pauldaniv.common.payload.base.WithIdDto
import org.springframework.core.io.ClassPathResource

final class DataProviderUtils {

  private static final Mapper map = new Mapper(new ObjectMapper())

  static <D extends WithIdDto> List<D> getData(String fileName, Class<D> cls) throws FileNotFoundException {
    initEntity(fileName, cls)
  }

  private static <D extends WithIdDto> List<D> initEntity(String entityJson,
                                                          Class<D> dto) {

    def entityDtos = new ClassPathResource("${entityJson}.json")
    return map.oMap.readValue(
        entityDtos.getFile(),
        map.oMap.getTypeFactory()
            .constructCollectionType(List, dto))
  }
}

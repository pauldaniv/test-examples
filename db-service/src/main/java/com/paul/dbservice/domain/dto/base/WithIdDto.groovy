package com.paul.dbservice.domain.dto.base

import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder

@Builder
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class WithIdDto {
  String id
}

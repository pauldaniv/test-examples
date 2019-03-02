package com.paul.common.payload

import com.paul.common.payload.base.WithIdDto
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.builder.Builder

@Builder
@EqualsAndHashCode
@ToString(includePackage = false)
class TestEntityDto extends WithIdDto {
  String firstName
  String lastName
}

package com.paul.common.payload.base

import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder

@Builder
@EqualsAndHashCode
class WithIdDto {
  Long id
}

package com.paul.common.payload

import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder;

@Builder
@EqualsAndHashCode
class IdsList {
  List<Long> ids
}

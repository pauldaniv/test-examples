package com.paul.common.payload.base

import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder

import java.time.LocalDateTime

@Builder
@EqualsAndHashCode
class WithDateDto extends WithIdDto {

  LocalDateTime createdTime
  LocalDateTime modifiedTime
}

package com.pauldaniv.common.payload

import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder

import java.time.LocalDate

@Builder
@EqualsAndHashCode
class CarSearchParams {
  String brand
  Boolean used
  LocalDate producedBefore
  LocalDate producedAfter
}

package com.paul.library.payload

import groovy.transform.builder.Builder

import java.time.LocalDate

@Builder
class SearchParams {
    String brand
    Boolean used
    LocalDate producedBefore
    LocalDate producedAfter
}

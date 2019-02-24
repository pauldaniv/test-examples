package com.paul.library.payload

import com.paul.library.payload.base.WithIdDto
import groovy.transform.builder.Builder

@Builder
class TestEntityDto extends WithIdDto {
    String firstName
    String lastName
}

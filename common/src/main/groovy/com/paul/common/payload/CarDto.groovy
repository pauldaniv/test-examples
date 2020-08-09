package com.paul.common.payload

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer
import com.paul.common.payload.base.WithDateDto
import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder

import java.time.YearMonth

@Builder
@EqualsAndHashCode
class CarDto extends WithDateDto {
  String brand
  String model
  Double price
  Integer count
  Boolean available
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM")
  @JsonDeserialize(using = YearMonthDeserializer)
  @JsonSerialize(using = YearMonthSerializer)
  YearMonth releasedIn
}

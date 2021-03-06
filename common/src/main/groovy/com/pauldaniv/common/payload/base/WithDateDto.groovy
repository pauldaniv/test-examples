package com.pauldaniv.common.payload.base

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder

import java.time.LocalDateTime

@Builder
@EqualsAndHashCode
class WithDateDto extends WithIdDto {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss")
  @JsonDeserialize(using = LocalDateTimeDeserializer)
  @JsonSerialize(using = LocalDateTimeSerializer)
  LocalDateTime createdTime

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss")
  @JsonDeserialize(using = LocalDateTimeDeserializer)
  @JsonSerialize(using = LocalDateTimeSerializer)
  LocalDateTime modifiedTime
}

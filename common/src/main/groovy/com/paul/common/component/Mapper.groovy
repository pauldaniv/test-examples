package com.paul.common.component

import com.fasterxml.jackson.databind.ObjectMapper
import org.modelmapper.ModelMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

import static java.util.stream.Collectors.toList
import static java.util.stream.StreamSupport.stream

@Component
class Mapper {

  final ObjectMapper oMap
  final ModelMapper mMap

  Mapper(ObjectMapper oMap) {
    this.oMap = oMap
    this.mMap = new ModelMapper()
  }

  def <T> T map(Object source, Class<T> targetClass) {
    mMap.map(source, targetClass)
  }

  def <T> List<T> map(Iterable sources, Class<T> targetClass) {
    stream(sources.spliterator(), false).map({ source -> map(source, targetClass) }).collect(toList())
  }

  def <T> Page<T> map(Page source, Class<T> targetClass, Pageable pageable) {
    new PageImpl(map(source.getContent(), targetClass), pageable, source.getTotalElements())
  }
}

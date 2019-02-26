package com.paul.common.component

import org.modelmapper.ModelMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Component
class Mapper {
  ModelMapper mapper

  def <T> T map(Object source, Class<T> targetClass) {
    mapper.map(source, targetClass);
  }

  def <T> List<T> map(Iterable<?> sources, Class<T> targetClass) {
    List<T> result = new ArrayList<>()
    sources.forEach({source -> result.add(map(source, targetClass))})
    result
  }

  def <T> Page<T> map(Page<?> source, Class<T> targetClass, Pageable pageable) {
    new PageImpl<>(map(source.getContent(), targetClass), pageable, source.getTotalElements())
  }

  @PostConstruct
  private void init() {
    mapper = new ModelMapper()
  }
}
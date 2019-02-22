package com.paul.library.component;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {
  private ModelMapper mapper;

  public <T> T map(Object source, Class<T> targetClass) {
    return mapper.map(source, targetClass);
  }

  public <T> List<T> map(Iterable<?> sources, Class<T> targetClass) {
    List<T> result = new ArrayList<>();
    sources.forEach(source -> result.add(map(source, targetClass)));
    return result;
  }

  public <T> Page<T> map(Page<?> source, Class<T> targetClass, Pageable pageable) {
    return new PageImpl<>(map(source.getContent(), targetClass), pageable, source.getTotalElements());
  }

  @PostConstruct
  private void init() {
    mapper = new ModelMapper();
  }
}
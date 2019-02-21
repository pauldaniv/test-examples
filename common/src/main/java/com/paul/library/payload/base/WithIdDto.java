package com.paul.library.payload.base;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@Data
@MappedSuperclass
@Accessors(chain = true)
public abstract class WithIdDto<T extends WithIdDto> {
  private Long id;
}

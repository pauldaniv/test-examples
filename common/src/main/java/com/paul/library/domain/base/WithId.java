package com.paul.library.domain.base;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;


@Data
@MappedSuperclass
@Accessors(chain = true)
public abstract class WithId <T extends WithId> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @SuppressWarnings("unchecked")
  public T setId(Long id) {

    this.id = id;
    return (T) this;
  }
}

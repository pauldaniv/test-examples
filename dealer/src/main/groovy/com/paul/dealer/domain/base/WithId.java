package com.paul.dealer.domain.base;

import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public class WithId<T extends WithId> {
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

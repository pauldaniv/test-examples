package com.paul.dealer.domain.base;

import javax.persistence.*;

@MappedSuperclass
public class WithId<T extends WithId> {
  @SuppressWarnings("unchecked")
  public T setId(Long id) {
    this.id = id;
    return (T) this;
  }

  public Long getId() {
    return id;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
}

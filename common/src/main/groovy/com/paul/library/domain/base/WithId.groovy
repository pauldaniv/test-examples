package com.paul.library.domain.base


import lombok.experimental.Accessors

import javax.persistence.*

@MappedSuperclass
@Accessors(chain = true)
abstract class WithId <T extends WithId> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @SuppressWarnings("unchecked")
  T setId(Long id) {

    this.id = id;
    return (T) this;
  }
}

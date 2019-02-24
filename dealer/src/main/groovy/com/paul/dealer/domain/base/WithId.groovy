package com.paul.dealer.domain.base

import groovy.transform.builder.Builder

import javax.persistence.*

@Builder
@MappedSuperclass
abstract class WithId <T extends WithId> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  Long id;

  @SuppressWarnings("unchecked")
  T setId(Long id) {

    this.id = id;
    return (T) this;
  }
}
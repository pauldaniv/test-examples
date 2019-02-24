package com.paul.dealer.domain

import com.paul.dealer.domain.base.WithId
import groovy.transform.builder.Builder

import javax.persistence.Entity
import javax.persistence.Table

@Builder
@Entity
@Table
class TestEntity extends WithId<TestEntity> {
  String firstName
  String lastName
}

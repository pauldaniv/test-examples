package com.paul.library.domain

import lombok.experimental.Accessors

import javax.persistence.*

@Accessors(chain = true)

@Entity
@Table
class TestEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  Long id;

  String firstName;
  String lastName;
}

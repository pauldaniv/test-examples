package com.paul.dealer.domain

import com.paul.dealer.domain.base.WithDate
import groovy.transform.builder.Builder

import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.Table
import java.time.LocalDate

@Builder
@Entity
@Table
 class Car extends WithDate<Car> {

  String brand
  String model
  LocalDate releasedIn

  @ManyToMany(mappedBy = "cars")
  List<Order> orders
}

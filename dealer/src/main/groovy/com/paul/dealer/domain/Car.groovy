package com.paul.dealer.domain

import com.paul.dealer.domain.base.WithDate
import groovy.transform.builder.Builder

import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.Table
import java.time.YearMonth

@Builder
@Entity
@Table
class Car extends WithDate<Car> {

  String brand
  String model
  YearMonth releasedIn

  @ManyToMany(mappedBy = "cars")
  List<Order> orders

  @Override
  public String toString() {
    return """\
Car{
    id=$id,
    createdTime=$createdTime,
    modifiedTime=$modifiedTime,
    brand='$brand', 
    model='$model', 
    releasedIn=$releasedIn, 
    orders=$orders
}"""
  }
}

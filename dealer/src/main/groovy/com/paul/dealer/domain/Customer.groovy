package com.paul.dealer.domain

import com.paul.dealer.domain.base.WithDate
import groovy.transform.builder.Builder

import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Builder
@Entity
@Table(name = "customers")
class Customer extends WithDate<Customer> {

  String fullName;

  @OneToMany(mappedBy = "customer")
  List<Order> orders

  @OneToMany(mappedBy = "customer")
  List<Invoice> invoices;
}

package com.paul.library.domain

import com.paul.library.domain.base.WithDate
import lombok.experimental.Accessors

import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Accessors(chain = true)
@Entity
@Table
class Customer extends WithDate<Customer> {

  String fullName;

  @OneToMany(mappedBy = "customer")
  List<Order> orders

  @OneToMany(mappedBy = "customer")
  List<Invoice> invoices;
}

package com.paul.dealer.domain

import com.paul.dealer.domain.base.WithDate
import groovy.transform.builder.Builder

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Builder
@Entity
@Table(name = "invoices")
class Invoice extends WithDate<Invoice> {


  Integer total

  @OneToMany(mappedBy = "invoice")
  List<Order> orders

  @ManyToOne
  Customer customer
}

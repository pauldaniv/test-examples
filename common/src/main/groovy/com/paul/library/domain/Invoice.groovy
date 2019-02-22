package com.paul.library.domain

import com.paul.library.domain.base.WithDate
import lombok.experimental.Accessors

import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Accessors(chain = true)
@Entity
@Table
class Invoice extends WithDate<Invoice> {


  Integer total

  @OneToMany(mappedBy = "invoice")
  List<Order> orders

  @ManyToOne
  Customer customer
}

package com.paul.dealer.domain

import com.paul.dealer.domain.base.WithDate
import groovy.transform.builder.Builder

import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Builder
@Entity
@Table
class Customer extends WithDate<Customer> {

  String fullName;

  @OneToMany(mappedBy = "customer")
  List<Order> orders

  @OneToMany(mappedBy = "customer")
  List<Invoice> invoices


    @Override
    public String toString() {
        return """\
Customer{
    id=$id,
    createdTime=$createdTime,
    modifiedTime=$modifiedTime,
    fullName='$fullName', 
    orders=$orders, 
    invoices=$invoices
}"""
    }
}

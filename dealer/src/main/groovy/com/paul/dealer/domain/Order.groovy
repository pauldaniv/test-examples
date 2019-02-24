package com.paul.dealer.domain

import com.paul.dealer.domain.base.WithDate
import groovy.transform.builder.Builder

import javax.persistence.*

@Builder
@Entity
@Table(name = "orders_table")
class Order extends WithDate<Order> {

  @ManyToOne
  Customer customer

  @ManyToOne
  Invoice invoice

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "car_orders",
          joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"))
  List<Car> cars
}

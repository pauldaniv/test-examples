package com.paul.library.domain

import com.paul.library.domain.base.WithDate
import lombok.EqualsAndHashCode
import lombok.experimental.Accessors

import javax.persistence.*

@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orders")
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

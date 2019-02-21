package com.paul.library.domain;

import com.paul.library.domain.base.WithDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends WithDate<Order> {

  @ManyToOne
  private Customer customer;

  @ManyToOne
  private Invoice invoice;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "car_orders",
          joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"))
  private List<Car> cars;
}

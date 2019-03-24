package com.paul.dealer.domain;

import com.paul.dealer.domain.base.WithDate;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@Entity
@Table(name = "orders")
public class Order extends WithDate<Order> {

    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Invoice invoice;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "car_orders",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"))
    private List<Car> cars;
}

package com.paul.dealer.domain;

import com.paul.dealer.domain.base.WithDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "customers")
public class Customer extends WithDate<Customer> {
    private String fullName;

    @OneToMany(mappedBy = "bookedBy")
    private List<Car> bookedCars;
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoices;
}

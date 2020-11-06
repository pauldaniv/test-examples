package com.pauldaniv.dealer.domain;

import com.pauldaniv.dealer.domain.base.WithDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

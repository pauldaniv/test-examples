package com.paul.dealer.domain;

import com.paul.dealer.domain.base.WithDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "invoices")
public class Invoice extends WithDate<Invoice> {

    private Integer total;
    @OneToMany(mappedBy = "invoice")
    private List<Order> orders;
    @ManyToOne
    private Customer customer;
}

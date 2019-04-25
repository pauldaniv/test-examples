package com.paul.dealer.domain;

import com.paul.dealer.domain.base.WithDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "invoices")
public class Invoice extends WithDate<Invoice> {

    private Double total;
    @OneToMany
    private List<Order> orders;
    @ManyToOne
    private Customer customer;
}

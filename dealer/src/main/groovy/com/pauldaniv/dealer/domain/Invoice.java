package com.pauldaniv.dealer.domain;

import com.pauldaniv.dealer.domain.base.WithDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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

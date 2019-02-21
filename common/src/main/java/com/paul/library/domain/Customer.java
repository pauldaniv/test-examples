package com.paul.library.domain;

import com.paul.library.domain.base.WithDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Customer extends WithDate<Customer> {

  private String fullName;

  @OneToMany(mappedBy = "customer")
  private List<Order> orders;

  @OneToMany(mappedBy = "customer")
  private List<Invoice> invoices;
}

package com.paul.library.domain;

import com.paul.library.domain.base.WithDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
public class Invoice extends WithDate<Invoice> {


  private Integer total;

  @OneToMany(mappedBy = "invoice")
  private List<Order> orders;

  @ManyToOne
  private Customer customer;
}

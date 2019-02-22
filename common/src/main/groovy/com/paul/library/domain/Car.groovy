package com.paul.library.domain;

import com.paul.library.domain.base.WithDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDate

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Car extends WithDate<Car> {

  private String brand;

  private String model;

  private LocalDate releasedIn;

  @ManyToMany(mappedBy = "cars")
  private List<Order> orders;
}

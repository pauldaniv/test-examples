package com.paul.dealer.domain;

import com.paul.dealer.domain.base.WithDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.YearMonth;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "cars")
public class Car extends WithDate<Car> {

    private String brand;
    private String model;
    private YearMonth releasedIn;
    @ManyToMany(mappedBy = "cars")
    private List<Order> orders;
}

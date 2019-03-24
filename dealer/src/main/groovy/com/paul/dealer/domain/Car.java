package com.paul.dealer.domain;

import com.paul.dealer.domain.base.WithDate;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.YearMonth;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@Entity
@Table(name = "cars")
public class Car extends WithDate<Car> {

    private String brand;
    private String model;
    private YearMonth releasedIn;
    private Double price;
    private Integer count;
    @ManyToOne
    private Customer bookedBy;
    @ManyToMany(mappedBy = "cars")
    private List<Order> orders;
}

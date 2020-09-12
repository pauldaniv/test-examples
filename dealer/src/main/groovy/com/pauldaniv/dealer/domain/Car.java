package com.pauldaniv.dealer.domain;

import com.pauldaniv.dealer.domain.base.WithDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.YearMonth;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
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

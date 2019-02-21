package com.paul.library.payload;

import com.paul.library.domain.base.WithDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CarDto extends WithDate<CarDto> {

  private String brand;

  private String model;

  private LocalDate releasedIn;

  private List<OrderDto> orders;
}

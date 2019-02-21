package com.paul.library.payload;

import com.paul.library.payload.base.WithIdDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto extends WithIdDto<OrderDto> {
  private InvoiceDto invoice;

  private List<CarDto> cars;
}

package com.paul.library.payload.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WithDateDto extends WithIdDto {

  LocalDateTime createdTime;
  LocalDateTime modifiedTime;
}

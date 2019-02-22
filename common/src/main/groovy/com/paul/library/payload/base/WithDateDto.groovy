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
public class WithDateDto<T extends WithDateDto> extends WithIdDto<T> {

  LocalDateTime createdTime;
  LocalDateTime modifiedTime;
}

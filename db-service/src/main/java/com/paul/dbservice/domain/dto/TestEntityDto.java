package com.paul.dbservice.domain.dto;

import com.paul.dbservice.domain.dto.base.WithIdDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TestEntityDto extends WithIdDto {
    private String firstName;
    private String lastName;
}

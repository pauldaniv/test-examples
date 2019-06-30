package com.paul.dbservice.domain;

import com.paul.dbservice.domain.base.WithId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TestEntity extends WithId<TestEntity> {
    private String firstName;
    private String lastName;
}

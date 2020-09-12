package com.pauldaniv.dealer.domain;

import com.pauldaniv.dealer.domain.base.WithId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "test_entities")
public class TestEntity extends WithId<TestEntity> {
    private String firstName;
    private String lastName;
}

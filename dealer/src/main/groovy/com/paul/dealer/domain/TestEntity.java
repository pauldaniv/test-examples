package com.paul.dealer.domain;

import com.paul.dealer.domain.base.WithId;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@Entity
@Table(name = "test_entities")
public class TestEntity extends WithId<TestEntity> {
  private String firstName;
  private String lastName;
}

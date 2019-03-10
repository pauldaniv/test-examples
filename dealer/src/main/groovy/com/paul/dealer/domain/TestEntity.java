package com.paul.dealer.domain;

import com.paul.dealer.domain.base.WithId;
import lombok.*;

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

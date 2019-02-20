package com.paul.library.domen;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@ToString
public class TestEntity {

  @Id
  private String id;

  private String firstName;
  private String lastName;

  public TestEntity() {
  }

  public TestEntity(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
}

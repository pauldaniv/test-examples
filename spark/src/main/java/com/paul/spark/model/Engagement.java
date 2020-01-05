package com.paul.spark.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
public class Engagement implements Serializable {
    private String guid;
    private String name;
    private Set<User> members;
    private Set<User> leaders;
    private Set<User> managers;
    private Set<String> auditUnits;
}

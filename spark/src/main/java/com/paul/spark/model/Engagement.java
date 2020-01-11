package com.paul.spark.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class Engagement implements Serializable {
    private String guid;
    private String name;
    private List<User> members;
    private List<User> leaders;
    private List<User> managers;
    private List<String> auditUnits;
}

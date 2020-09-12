package com.pauldaniv.spark.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {
    private String guid;
    private String email;
    private String fullName;
}

package com.paul.spark.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DwellingsStatistic implements Serializable {
    private Timestamp month;
    private Integer sa2Code;
    private String sa2Name;
    private String territorialAuthority;
    private Integer totalDwellingUnits;
    private Integer houses;
    private Integer apartments;
    private Integer retirementVillageUnits;
    private Integer townhousesFlatUnitsOther;
}

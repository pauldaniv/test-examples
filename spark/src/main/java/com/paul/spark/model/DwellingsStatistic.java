package com.paul.spark.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "The number of houses at given month",
            example = "500",
            required = true,
            accessMode = Schema.AccessMode.READ_ONLY)
    private Integer houses;
    private Integer apartments;
    private Integer retirementVillageUnits;
    private Integer townhousesFlatUnitsOther;
}

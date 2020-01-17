package com.paul.spark.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Statistic {
    private String userId;
    private String engagementName;
    private Long consultationsCount;
    private Date date;
}

package com.paul.spark.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OverallEngagementStatistic {
    private String userId;
    private Long engagementsCount;
    private Long consultationsCount;
    private Date date;
}

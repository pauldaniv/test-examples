package com.paul.spark.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EngagementStatistic {
    private String userId;
    private String userName;
    private String engagementName;
    private Long consultationsCount;
    private LocalDateTime date;
}

package com.pauldaniv.spark.services;

import com.pauldaniv.spark.model.DwellingsStatistic;

import java.util.List;

public interface DatasetExample {
    List<DwellingsStatistic> loadData(String fileName);
}

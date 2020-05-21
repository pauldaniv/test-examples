package com.paul.spark.services.dataset;

import com.paul.spark.model.DwellingsStatistic;

import java.util.List;

public interface DatasetExample {
    List<DwellingsStatistic> loadData(String fileName);
}

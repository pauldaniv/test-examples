package com.paul.spark.dataset;

import com.paul.spark.model.DwellingsStatistic;

import java.util.List;

public interface DatasetExample {
    List<DwellingsStatistic> loadData(String fileName);
}

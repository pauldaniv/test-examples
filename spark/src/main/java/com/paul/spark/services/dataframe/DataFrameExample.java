package com.paul.spark.services.dataframe;

import com.paul.spark.model.Engagement;
import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;
import java.util.List;

public interface DataFrameExample extends Serializable {
    Long countColumns(String fileName);

    Long countRows(String fileName);

    Long countRowsDistinct(String fileName);

    List<Engagement> collectAuraData();

    JavaRDD<Engagement> getAuraRDD();
}
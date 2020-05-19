package com.paul.spark.dataframe;

import com.paul.spark.model.Engagement;
import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;

public interface DataFrameExample extends Serializable {
    Long countColumns(String fileName);

    Long countRows(String fileName);

    Long countRowsDistinct(String fileName);

    JavaRDD<Engagement> collectAuraData();
}

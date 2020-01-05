package com.paul.spark.dataframe;

import com.paul.spark.model.Engagement;

import java.io.Serializable;
import java.util.List;

public interface DataFrameExample extends Serializable {
    Long countColumns(String fileName);

    Long countRows(String fileName);

    Long countRowsDistinct(String fileName);

    List<Engagement> collectData(String fileName);
}

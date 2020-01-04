package com.paul.spark.dataframe;

import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DataframeExampleImpl implements DataframeExample {

    private final SQLContext sqlContext;

    @Override
    public Long countColumns(String fileName) {
        return (long) getDataFrame(fileName).columns().length;
    }

    @Override
    public Long countRows(String fileName) {
        final Dataset<Row> dataFrame = getDataFrame(fileName);
        dataFrame.show();
        return dataFrame.toJavaRDD().count();
    }

    private Dataset<Row> getDataFrame(String fileName) {
        return sqlContext.read()
                .csv("spark/data/" + fileName)
                .toDF("name", "age");
    }
}

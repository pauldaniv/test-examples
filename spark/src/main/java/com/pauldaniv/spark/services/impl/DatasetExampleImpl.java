package com.pauldaniv.spark.services.impl;

import static org.apache.spark.sql.types.DataTypes.IntegerType;
import static org.apache.spark.sql.types.DataTypes.StringType;
import static org.apache.spark.sql.types.DataTypes.TimestampType;
import static org.apache.spark.sql.types.DataTypes.createStructField;
import static org.apache.spark.sql.types.DataTypes.createStructType;

import com.pauldaniv.spark.model.DwellingsStatistic;
import com.pauldaniv.spark.services.ResourceResolver;
import com.pauldaniv.spark.services.DatasetExample;
import lombok.RequiredArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SQLContext;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DatasetExampleImpl implements DatasetExample {

    private final SQLContext sqlContext;
    private final ResourceResolver resourceResolver;

    @Override
    public List<DwellingsStatistic> loadData(String fileName) {
        final Dataset<DwellingsStatistic> dataset = getDataset(fileName);
        return dataset.takeAsList(500);
    }

    private Dataset<DwellingsStatistic> getDataset(String fileName) {
        return sqlContext.read()
                .format("csv")
                .option("header", true)
                .option("timestampFormat", "yyyy-MM-dd")
                .schema(createStructType(Arrays.asList(
                        createStructField("month", TimestampType, false),
                        createStructField("sa2Code", IntegerType, false),
                        createStructField("sa2Name", StringType, false),
                        createStructField("territorialAuthority", StringType, false),
                        createStructField("totalDwellingUnits", IntegerType, false),
                        createStructField("houses", IntegerType, false),
                        createStructField("apartments", IntegerType, false),
                        createStructField("retirementVillageUnits", IntegerType, false),
                        createStructField("townhousesFlatUnitsOther", IntegerType, false))))
                .csv(resourceResolver.resolve(fileName))
                .as(Encoders.bean(DwellingsStatistic.class));
    }
}

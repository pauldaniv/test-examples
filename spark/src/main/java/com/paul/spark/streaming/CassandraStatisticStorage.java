package com.paul.spark.streaming;

import com.datastax.spark.connector.japi.CassandraRow;
import com.datastax.spark.connector.japi.rdd.CassandraTableScanJavaRDD;
import com.paul.spark.model.EngagementStatistic;
import lombok.RequiredArgsConstructor;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

@Service
@RequiredArgsConstructor
public class CassandraStatisticStorage implements StatisticStorage {

    private final JavaSparkContext sparkContext;

    @Override
    public void store(JavaPairRDD<String, Tuple2<Tuple2<String, String>, Tuple2<String, Long>>> data) {
        data.foreach(it -> {
            CassandraTableScanJavaRDD<CassandraRow> statistic =
                    javaFunctions(sparkContext)
                            .cassandraTable("aura", "statistic")
                            .where("userId = '" + it._1 + "'")
                            .where("engagementName = '" + it._2._1 + "'");

            if (statistic.isEmpty()) {
                storeRecord(new EngagementStatistic(it._1, it._2._2._1, it._2._1._1, it._2._1._2));
            } else {
                CassandraTableScanJavaRDD<CassandraRow> hourlyStatistic =
                        javaFunctions(sparkContext)
                                .cassandraTable("aura", "hourly_statistic")
                                .where("userId = '" + it._1 + "'");
            }
        });
    }


    private void storeRecord(final EngagementStatistic record) {
        final JavaRDD<EngagementStatistic> rdd = sparkContext.<EngagementStatistic>parallelize(Collections.singletonList(record));
        javaFunctions(rdd)
                .writerBuilder("aura", "statistic", mapToRow(EngagementStatistic.class))
                .saveToCassandra();
    }

}

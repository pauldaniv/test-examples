package com.paul.spark.streaming;

import com.datastax.spark.connector.japi.CassandraRow;
import com.datastax.spark.connector.japi.rdd.CassandraTableScanJavaRDD;
import com.paul.spark.model.DetailedEngagementStatistic;
import lombok.RequiredArgsConstructor;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.stereotype.Service;
import scala.Tuple2;
import scala.Tuple3;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;
import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;

@Service
@RequiredArgsConstructor
public class CassandraStatisticStorage implements StatisticStorage {

    private final JavaSparkContext sparkContext;

    @Override
    public void store(JavaPairRDD<String, Tuple2<Tuple2<String, Long>, Tuple3<String, Long, LocalDateTime>>> data) {
        data.foreach(it -> {
            CassandraTableScanJavaRDD<CassandraRow> detailedStatistic =
                    javaFunctions(sparkContext)
                            .cassandraTable("aura", "detailed_statistic")
                            .where("userId = '" + it._1 + "'")
                            .where("engagement_name = '" + it._2._1 + "'")
                            .where("date_plus(date, 1, hour) < '" + it._2._2._3() + "'" );

            CassandraTableScanJavaRDD<CassandraRow> overallStatistic =
                    javaFunctions(sparkContext)
                            .cassandraTable("aura", "overall_statistic")
                            .where("userId = '" + it._1 + "'")
                            .where("engagement_name = '" + it._2._1 + "'");

            detailedStatistic



            storeRecord(new DetailedEngagementStatistic(it._1, it._2._1._1, it._2._1._2, new Date()));

        });
    }


    private void storeRecord(final DetailedEngagementStatistic record) {
        final JavaRDD<DetailedEngagementStatistic> rdd = sparkContext.parallelize(Collections.singletonList(record));
        javaFunctions(rdd)
                .writerBuilder("aura", "statistic", mapToRow(DetailedEngagementStatistic.class))
                .saveToCassandra();
    }

}

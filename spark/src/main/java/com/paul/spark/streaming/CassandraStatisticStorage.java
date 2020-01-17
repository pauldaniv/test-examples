package com.paul.spark.streaming;

import com.datastax.spark.connector.japi.CassandraRow;
import com.datastax.spark.connector.japi.rdd.CassandraTableScanJavaRDD;
import com.paul.spark.model.Statistic;
import lombok.RequiredArgsConstructor;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.stereotype.Service;
import scala.Tuple2;
import scala.Tuple3;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

@Service
@RequiredArgsConstructor
public class CassandraStatisticStorage implements StatisticStorage {

    private final JavaSparkContext sparkContext;

    @Override
    public void store(JavaPairRDD<String, Tuple2<Tuple2<String, Long>, Tuple3<String, Long, LocalDateTime>>> data) {
        data.foreach(it -> {

            CassandraTableScanJavaRDD<CassandraRow> statistic =
                    javaFunctions(sparkContext)
                            .cassandraTable("aura", "statistic")
                            .where("user_id = '" + it._1 + "'")
                            .where("engagement_name = '" + it._2._1 + "'");

            final Statistic recordToStore = Statistic.builder()
                    .userId(it._1)
                    .engagementName(it._2._1._1)
                    .date(convertTime(it._2._2._3()))
                    .build();

            if (statistic.isEmpty()) {
                recordToStore.setConsultationsCount(it._2._1._2);
            } else {
                final CassandraRow entry = statistic.first();
                recordToStore.setConsultationsCount(entry.getLong("consultations_count") + it._2._2._2());
            }

            storeRecord(recordToStore);
        });
    }

    private void storeRecord(final Statistic record) {
        final JavaRDD<Statistic> rdd = sparkContext.parallelize(Collections.singletonList(record));
        javaFunctions(rdd)
                .writerBuilder("aura", "statistic", mapToRow(Statistic.class))
                .saveToCassandra();
    }

    private Date convertTime(final LocalDateTime time) {
        return Date.from(time.atZone(ZoneOffset.systemDefault()).toInstant());
    }
}

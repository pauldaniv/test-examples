package com.paul.spark.services.impl.streaming;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

import com.datastax.spark.connector.japi.CassandraRow;
import com.datastax.spark.connector.japi.rdd.CassandraTableScanJavaRDD;
import com.paul.spark.config.Config;
import com.paul.spark.model.Statistic;
import lombok.RequiredArgsConstructor;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.springframework.stereotype.Service;
import scala.Tuple3;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CassandraStatisticStorage implements StatisticPersistence<String, Tuple3<String, Long, LocalDateTime>> {

    @Override
    public JavaRDD<Statistic> store(final JavaPairRDD<String, Tuple3<String, Long, LocalDateTime>> data) {
        return data.map(it -> {

            CassandraTableScanJavaRDD<CassandraRow> statistic =
                    javaFunctions(Config.javaSparkContextSupplier.get())
                            .cassandraTable("aura", "statistic")
                            .where("user_id = '" + it._1 + "'")
                            .where("engagement_name = '" + it._2._1() + "'");

            final Statistic recordToStore = Statistic.builder()
                    .user_id(it._1)
                    .engagement_name(it._2._1())
                    .date(convertTime(it._2._3()))
                    .build();

            if (statistic.isEmpty()) {
                recordToStore.setConsultations_count(it._2._2());
            } else {
                final CassandraRow entry = statistic.first();
                recordToStore.setConsultations_count(entry.getLong("consultations_count") + it._2._2());
            }

            storeRecord(recordToStore);
            return recordToStore;
        });
    }

    private void storeRecord(final Statistic record) {
        final JavaRDD<Statistic> rdd = Config.javaSparkContextSupplier.get()
                .parallelize(Collections.singletonList(record));
        javaFunctions(rdd)
                .writerBuilder("aura", "statistic", mapToRow(Statistic.class))
                .saveToCassandra();
    }

    private Date convertTime(final LocalDateTime time) {
        return Date.from(time.atZone(ZoneOffset.systemDefault()).toInstant());
    }
}

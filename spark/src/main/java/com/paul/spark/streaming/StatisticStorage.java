package com.paul.spark.streaming;

import com.paul.spark.model.Statistic;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import scala.Tuple3;

import java.io.Serializable;
import java.time.LocalDateTime;

public interface StatisticStorage extends Serializable {
    JavaRDD<Statistic> store(JavaPairRDD<String, Tuple3<String, Long, LocalDateTime>> data);
}

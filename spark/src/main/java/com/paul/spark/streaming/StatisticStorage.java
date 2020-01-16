package com.paul.spark.streaming;

import org.apache.spark.api.java.JavaPairRDD;
import scala.Tuple2;
import scala.Tuple3;

import java.time.LocalDateTime;

public interface StatisticStorage {

    void store(JavaPairRDD<String, Tuple2<Tuple2<String, Long>, Tuple3<String, Long, LocalDateTime>>> data);
}

package com.paul.spark.streaming;

import org.apache.spark.api.java.JavaPairRDD;
import scala.Tuple2;

public interface StatisticStorage {

    void store(JavaPairRDD<String, Tuple2<Tuple2<String, String>, Tuple2<String, Long>>> data);
}

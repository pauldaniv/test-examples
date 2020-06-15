package com.paul.spark.services.impl.streaming;

import com.paul.spark.model.Statistic;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;

public interface StatisticPersistence<K extends Serializable, V extends Serializable> extends Serializable {
    JavaRDD<Statistic> store(JavaPairRDD<K, V> data);
}

package com.paul.spark.graphx;

import org.apache.spark.api.java.JavaPairRDD;
import scala.Tuple2;

import java.util.List;

public interface GraphXExample {
    JavaPairRDD<String, Tuple2<String, Long>> getGraphRDD();

    List<Tuple2<String, Tuple2<String, Long>>> getGraphResult(int limit);
}

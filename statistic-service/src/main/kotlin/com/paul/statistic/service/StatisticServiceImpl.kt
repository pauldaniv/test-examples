package com.paul.statistic.service

import org.apache.spark.api.java.JavaSparkContext
import org.springframework.stereotype.Service
import scala.Serializable
import scala.Tuple2

@Service
class StatisticServiceImpl(private val sparkContext: JavaSparkContext) : StatisticService {
  override fun analyze(words: List<String>): Map<String, Long> {
    return sparkContext.parallelize(words)
        .map { it.toLowerCase() }
        .map { it.substring(5) }
        .filter { it.contains("e") }
        .mapToPair { Tuple2(it, 1L) }
        .reduceByKey { a, b -> a + b }
        .map { Pair(it._1, it._2) }
        .takeOrdered(10, serialize(object : Cmp<Pair<String, Long>> {
          override fun compare(p0: Pair<String, Long>, p1: Pair<String, Long>): Int {
            return p0.second.compareTo(p1.second)
          }
        }).reversed())
        .toMap()
  }

  private fun <T> serialize(cmp: Cmp<T>): Cmp<T> {
    return cmp
  }
}

@FunctionalInterface
interface Cmp<T> : Comparator<T>, Serializable

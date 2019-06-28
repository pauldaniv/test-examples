package com.paul.statistic.service

import java.io.Serializable

interface StatisticService : Serializable {
  fun analyze(words: List<String>): Map<String, Long>
}

package com.pauldaniv.statistic.service

import java.io.Serializable

interface StatisticService : Serializable {
  fun analyze(words: List<String>): Map<String, Long>
}

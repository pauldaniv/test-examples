package com.paul.statistic.controller

import com.paul.statistic.service.StatisticService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/statistic")
class StatisticController(private val statisticService: StatisticService) {

  @PostMapping
  fun analyze(@RequestBody words: List<String>): ResponseEntity<Any> {
    return ResponseEntity(statisticService.analyze(words), HttpStatus.OK)
  }
}

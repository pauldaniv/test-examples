package com.paul.spark.controller;

import com.paul.spark.model.ConsultationSubmit;
import com.paul.spark.model.Statistic;
import com.paul.spark.streaming.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spark/streaming/produce")
@RequiredArgsConstructor
public class ProducerController {

    private final ProducerService producerService;

    @PostMapping()
    private void push(@RequestBody final Statistic message) {
        producerService.sendMessage(message);
    }
}

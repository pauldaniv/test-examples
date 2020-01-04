package com.paul.spark.controller;

import com.paul.common.payload.Resp;
import com.paul.spark.dataset.DatasetExample;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spark/dataset")
@RequiredArgsConstructor
public class DatasetController {
    private final DatasetExample datasetExample;

    @GetMapping("/count/{fileName}")
    ResponseEntity<Resp<Long>> count(@PathVariable String fileName) {
        return Resp.ok(datasetExample.count(fileName));
    }
}

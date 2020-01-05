package com.paul.spark.controller;

import com.paul.common.payload.Resp;
import com.paul.spark.dataframe.DataFrameExample;
import com.paul.spark.model.Engagement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/spark/dataframe")
@RequiredArgsConstructor
public class DataframeController {
    private final DataFrameExample dataframeService;

    @GetMapping("/count/columns/{name}")
    ResponseEntity<Resp<Long>> countColumns(@PathVariable String name) {
        return Resp.ok(dataframeService.countColumns(name));
    }

    @GetMapping("/count/rows/{name}")
    ResponseEntity<Resp<List<Engagement>>> countRows(@PathVariable String name) {
        return Resp.ok(dataframeService.collectData(name));
    }
}

package com.paul.spark.controller;

import com.paul.common.payload.Resp;
import com.paul.spark.dataframe.DataframeExample;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spark/dataframe")
@RequiredArgsConstructor
public class DataframeController {
    private final DataframeExample dataframeService;

    @GetMapping("/count/columns/{name}")
    ResponseEntity<Resp<Long>> countColumns(@PathVariable String name) {
        return Resp.ok(dataframeService.countColumns(name));
    }

    @GetMapping("/count/rows/{name}")
    ResponseEntity<Resp<Long>> countRows(@PathVariable String name) {
        return Resp.ok(dataframeService.countRows(name));
    }
}

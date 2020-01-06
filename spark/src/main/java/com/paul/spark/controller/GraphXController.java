package com.paul.spark.controller;

import com.paul.common.payload.Resp;
import com.paul.spark.graphx.GraphXExample;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/spark/graphx")
@RequiredArgsConstructor
public class GraphXController {

    private final GraphXExample graphXExample;

    @GetMapping("/test")
    ResponseEntity<Resp> test() {
        graphXExample.getGraph();
        return Resp.ok();
    }
}

package com.paul.spark.controller;

import com.paul.spark.graphx.GraphXExampleImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scala.Tuple2;

import java.util.List;


@RestController
@RequestMapping("/api/spark/graphx")
@RequiredArgsConstructor
public class GraphXController {

    private final GraphXExampleImpl graphXExample;

    @GetMapping("/test/{limit}")
    ResponseEntity<List<Tuple2<String, Long>>> test(@PathVariable int limit) {
        return ResponseEntity.ok(graphXExample.getGraph(limit));
    }
}

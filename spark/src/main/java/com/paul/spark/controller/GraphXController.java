package com.paul.spark.controller;

import com.paul.spark.services.graphx.GraphXExampleImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/graphx")
@RequiredArgsConstructor
public class GraphXController {

    private final GraphXExampleImpl graphXExample;

    @GetMapping("/test/{limit}")
    ResponseEntity<List<GraphXExampleImpl.EngagementView>> test(@PathVariable int limit) {
        return ResponseEntity.ok(graphXExample.mostImportantEngagementLeaders(limit));
    }
}

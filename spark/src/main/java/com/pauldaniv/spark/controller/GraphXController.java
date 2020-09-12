package com.pauldaniv.spark.controller;

import com.pauldaniv.spark.services.impl.GraphXExampleImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import javax.validation.constraints.Positive;

@Tag(name = "GraphX Controller", description = "Used to perform basic operations with the GraphX API")
@RestController
@RequestMapping("/api/v1/graphx")
@RequiredArgsConstructor
public class GraphXController {

    private final GraphXExampleImpl graphXExample;

    @Operation(
            summary = "Get top N department leadres",
            description = "Get top N leaders across all the departments by their importance",
            tags = "graphx"
    )
    @GetMapping("/top/{limit}")
    ResponseEntity<List<GraphXExampleImpl.LeaderInfo>> top(
            @Parameter(description = "The number of department leaders to be shown")
            @PathVariable @Positive int limit) {
        return ResponseEntity.ok(graphXExample.mostImportantDepartmentLeaders(limit));
    }
}

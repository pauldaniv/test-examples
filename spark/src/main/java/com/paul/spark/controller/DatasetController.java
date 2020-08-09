package com.paul.spark.controller;

import com.paul.spark.model.DwellingsStatistic;
import com.paul.spark.services.DatasetExample;
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

@Tag(name = "Dataset Controller", description = "Used to perform basic operations with the dataset")
@RestController
@RequestMapping("/api/v1/dataset")
@RequiredArgsConstructor
public class DatasetController {
    private final DatasetExample datasetExample;

    @Operation(
            summary = "Load CSV file",
            description = "Load, process and return the data of specified CSV file",
            tags = {"dataset", "load"}
    )
    @GetMapping("/load-data/{fileName}")
    ResponseEntity<List<DwellingsStatistic>> loadData(
            @Parameter(
                    description = "The name of the file to be used to load the data",
                    example = "my_file.csv"
            )
            @PathVariable String fileName) {
        return ResponseEntity.ok(datasetExample.loadData(fileName));
    }
}

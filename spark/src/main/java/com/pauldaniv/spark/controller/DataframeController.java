package com.pauldaniv.spark.controller;

import com.pauldaniv.spark.model.Engagement;
import com.pauldaniv.spark.services.DataFrameExample;
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

@Tag(name = "Dataframe Controller", description = "Used to perform basic operations with the dataframes")
@RestController
@RequestMapping("/api/v1/dataframe")
@RequiredArgsConstructor
public class DataframeController {
    private final DataFrameExample dataframeService;

    @Operation(
            summary = "Count columns",
            description = "Count number of columns of specified CSV file",
            tags = {"dataframe", "count"}
    )
    @GetMapping("/count/columns/{fileName}")
    ResponseEntity<Long> countColumns(
            @Parameter(description = "The name of the file to be used to count the number of columns")
            @PathVariable String fileName) {
        return ResponseEntity.ok(dataframeService.countColumns(fileName));
    }

    @Operation(
            summary = "Count rows",
            description = "Count number of rows of specified CSV file",
            tags = {"dataframe", "count"}
    )
    @GetMapping("/count/rows/{name}")
    ResponseEntity<Long> countRows(
            @Parameter(description = "The name of the file to be used to count the number of rows")
            @PathVariable String name) {
        return ResponseEntity.ok(dataframeService.countRows(name));
    }

    @Operation(
            summary = "Load data",
            description = "Collect and display data of specified CSV file",
            tags = {"dataframe", "load"}
    )
    @GetMapping("/load-data/{name}")
    ResponseEntity<List<Engagement>> collectData(
            @Parameter(description = "The name of the file to be used to collect data")
            @PathVariable String name) {
        return ResponseEntity.ok(dataframeService.collectAuraData());
    }
}

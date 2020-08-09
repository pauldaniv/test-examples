package com.paul.dbservice.controller;

import com.paul.common.payload.Resp;
import com.paul.dbservice.domain.dto.TestEntityDto;
import com.paul.dbservice.service.DefaultService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dbservice/test-entity")
@RequiredArgsConstructor
public class TestEntityController {

    private final DefaultService service;

    @GetMapping("/{id}")
    ResponseEntity getOne(@PathVariable("id") String id) {
        service.getOne(id);
        return Resp.ok("testMessage", HttpStatus.UNAUTHORIZED);
    }

    @GetMapping
    ResponseEntity<Resp<List<TestEntityDto>>> getAll() {
        return service.getAll();
    }

    @PutMapping
    ResponseEntity update(@RequestBody TestEntityDto entity) {
        return service.update(entity);
    }

    @PostMapping
    ResponseEntity save(@RequestBody TestEntityDto entity) {
        return service.save(entity);
    }
}

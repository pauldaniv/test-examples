package com.paul.dealer.controller;

import com.paul.dealer.service.DefaultServiceImpl;
import com.paul.library.domen.TestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dealer")
public class DealerController {

    private final DefaultServiceImpl defaultService;

    public DealerController(DefaultServiceImpl defaultService) {
        this.defaultService = defaultService;
    }

    @GetMapping("/all")
    public Iterable<TestEntity> getAll() {
        return defaultService.getAll();
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody TestEntity entity) {
        return ResponseEntity.ok(defaultService.save(entity));
    }
}

package com.paul.dealer.controller;

import com.paul.dealer.service.DefaultService;
import com.paul.library.payload.Resp;
import com.paul.library.payload.TestEntityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dealer")
@RequiredArgsConstructor
public class DealerController {

    private final DefaultService defaultService;

    @GetMapping("/{id}")
    public ResponseEntity<Resp<TestEntityDto>> getOne(@PathVariable("id") Long id) {
        return defaultService.getOne(id);
    }

    @GetMapping("/all")
    public Iterable<TestEntityDto> getAll() {
        return defaultService.getAll();
    }

    @PostMapping("/save")
    public ResponseEntity<Resp<TestEntityDto>> save(@RequestBody TestEntityDto entity) {
        ResponseEntity<Resp<TestEntityDto>> save = defaultService.save(entity);
        return save;
    }
}

package com.paul.dealer;

import com.paul.library.domen.TestEntity;
import com.paul.dealer.service.DefaultService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@SpringBootApplication(scanBasePackages = {"com.paul.dealer", "com.paul.library"})
@RestController
public class DealerApplication {

  private final DefaultService defaultService;

  public DealerApplication(DefaultService defaultService) {
    this.defaultService = defaultService;
  }


  public static void main(String[] args) {
    SpringApplication.run(DealerApplication.class, args);
  }

  @GetMapping("/index")
  public String home() {
    return "my response 2";
  }

  @GetMapping("/all")
  public List<TestEntity> getAll() {
    return defaultService.getAll();
  }

  @PostMapping("/save")
  public ResponseEntity<String> save (TestEntity entity) {
    return ResponseEntity.ok(defaultService.save(entity));
  }
}

package com.paul.dealer.controller.infra

import com.paul.common.payload.Resp
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class InfraController {
  @GetMapping("/healthCheck")
  ResponseEntity<Resp> healthCheck() {
    Resp.ok()
  }
}

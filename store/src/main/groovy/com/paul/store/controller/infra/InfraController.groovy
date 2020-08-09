package com.paul.store.controller.infra

import com.paul.common.payload.Resp
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import static com.paul.common.payload.Resp.ok

@RestController
class InfraController {
  @GetMapping('/healthCheck')
  ResponseEntity<Resp> healthCheck() {
    ok()
  }
}

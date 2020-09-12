package com.pauldaniv.common.exception.handling

import com.pauldaniv.common.payload.Resp
import org.hibernate.ObjectNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import springfox.documentation.annotations.ApiIgnore

import static com.pauldaniv.common.payload.Resp.ok

@ApiIgnore
@ControllerAdvice
class ErrorHandler {
  @ExceptionHandler([ObjectNotFoundException])
  ResponseEntity<Resp> objectNotFound(def e) {
    ok("Entity ${e.entityName} with id: ${e.identifier} not found", false)
  }
}

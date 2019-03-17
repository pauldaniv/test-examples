package com.paul.common.exception.handling

import com.paul.common.payload.Resp
import org.hibernate.ObjectNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ErrorHandler {
  @ExceptionHandler([ObjectNotFoundException.class])
  ResponseEntity<Resp> objectNotFound(ObjectNotFoundException exception) {
    Resp.ok("Entity ${exception.entityName} with id: ${exception.identifier} not found", false)
  }
}

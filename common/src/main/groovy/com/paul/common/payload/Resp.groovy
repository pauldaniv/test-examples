package com.paul.common.payload

import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.EqualsAndHashCode
import groovy.transform.builder.Builder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.OK

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
class Resp<T> {

  private static String SUCCESS_MESSAGE = "success"

  String message
  boolean success
  T body

  Resp() {
  }

  Resp(Resp parent) {
    this.message = parent.message
    this.success = parent.success
  }

  Resp(final String message, final boolean success) {
    this.message = message
    this.success = success
  }

  Resp(final def body, final String message, final boolean success) {
    this(message, success)
    this.body = body
  }

  static ResponseEntity<Resp> ok() {
    ok(SUCCESS_MESSAGE)
  }

  static ResponseEntity<Resp> ok(String message) {
    ok(message, OK)
  }

  static ResponseEntity<Resp> ok(String message, boolean success) {
    new ResponseEntity<>(new Resp<>(message, success), OK)
  }

  static ResponseEntity<Resp> ok(body, String message, boolean success) {
    new ResponseEntity<>(new Resp<>(body, message, success), OK)
  }

  static <T> ResponseEntity<Resp<T>> ok(String message, T body) {
    ok(message, body, OK)
  }

  static <T> ResponseEntity<Resp<T>> ok(T body) {
    ok(SUCCESS_MESSAGE, body)
  }

  static ResponseEntity<Resp> ok(String message, HttpStatus status) {
    new ResponseEntity<>(new Resp(message, true), status)
  }

  static <T> ResponseEntity<Resp> ok(String message, T body, HttpStatus status) {
    new ResponseEntity<>(new Resp<>(body, message, true), status)
  }

  static ResponseEntity<Resp> fail() {
    fail(BAD_REQUEST)
  }

  static ResponseEntity<Resp> fail(String message) {
    new ResponseEntity<>(new Resp(message, false), BAD_REQUEST)
  }

  static ResponseEntity<Resp> fail(HttpStatus status) {
    new ResponseEntity<>(new Resp("Fail", false), status)
  }

  static ResponseEntity<Resp> fail(String message, HttpStatus status) {
    new ResponseEntity<>(new Resp(message, false), status)
  }

  static <T> ResponseEntity<Resp> fail(T body, String message, HttpStatus status) {
    new ResponseEntity<>(new Resp<>(body, message, false), status)
  }

  static ResponseEntity<Resp> fail(String message, Exception exception) {
    new ResponseEntity<>(
        new ResponseExceptionDetailDTO(message, exception.toString()),
        HttpStatus.INTERNAL_SERVER_ERROR)
  }

  static ResponseEntity<Resp> fail(String message, HttpStatus status,
                                   Exception exception) {
    return new ResponseEntity<>(new ResponseExceptionDetailDTO(message, exception.toString()), status)
  }

  static ResponseEntity<Resp> userNotExist() {
    return fail("User not exist", BAD_REQUEST)
  }

  static ResponseEntity<Resp> categoryNotExist() {
    return fail("Category not exist!", BAD_REQUEST)
  }


  @Builder
  @EqualsAndHashCode
  private static class ResponseExceptionDetailDTO extends Resp {
    String exception

    ResponseExceptionDetailDTO(Resp parent) {
      super(parent)
    }


    ResponseExceptionDetailDTO(String message, String exception) {
      super(message, false)
      this.exception = exception
    }

    String getException() {
      exception
    }

    ResponseExceptionDetailDTO setException(String exception) {
      this.exception = exception
      this
    }
  }
}

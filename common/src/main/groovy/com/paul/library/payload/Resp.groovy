package com.paul.library.payload


import com.fasterxml.jackson.annotation.JsonInclude
import lombok.Data
import lombok.EqualsAndHashCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
class Resp<T> {

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
        this.message = message;
        this.success = success;
    }

    Resp(final T body, final String message, final boolean success) {
        this(message, success);
        this.body = body;
    }

    static ResponseEntity<Resp> ok() {
        new ResponseEntity<>(new Resp("Success", true), HttpStatus.OK)
    }

    static ResponseEntity<Resp> ok(String message) {
        new ResponseEntity<>(new Resp(message, true), HttpStatus.OK)
    }

    static ResponseEntity<Resp> ok(String message, HttpStatus status) {
        new ResponseEntity<>(new Resp(message, true), status)
    }

    static <T> ResponseEntity<Resp<T>> ok(String message, T body) {
        return new ResponseEntity<>(new Resp<>(body, message, true), HttpStatus.OK)
    }

    static <T> ResponseEntity<Resp> ok(String message, T body, HttpStatus status) {
        new ResponseEntity<>(new Resp<>(body, message, true), status)
    }

    static <T> ResponseEntity<Resp<T>> ok(T body) {
        ok("Success", body)
    }

    static ResponseEntity<Resp> failureResp() {
        new ResponseEntity<>(new Resp("Fail", false), HttpStatus.BAD_REQUEST);
    }

    static ResponseEntity<Resp> failureResp(String message) {
        new ResponseEntity<>(new Resp(message, false), HttpStatus.BAD_REQUEST)
    }

    static ResponseEntity<Resp> failureResp(HttpStatus status) {
        new ResponseEntity<>(new Resp("Fail", false), status)
    }

    static ResponseEntity<Resp> failureResp(String message, HttpStatus status) {
        new ResponseEntity<>(new Resp(message, false), status)
    }

    static <T> ResponseEntity<Resp> failureResp(T body, String message, HttpStatus status) {
        new ResponseEntity<>(new Resp<>(body, message, false), status)
    }

    static ResponseEntity<Resp> failureResp(String message, Exception exception) {
        new ResponseEntity<>(
                new ResponseExceptionDetailDTO(message, exception.toString()),
                HttpStatus.INTERNAL_SERVER_ERROR)
    }

    static ResponseEntity<Resp> failureResp(String message, HttpStatus status,
                                            Exception exception) {
        return new ResponseEntity<>(new ResponseExceptionDetailDTO(message, exception.toString()), status)
    }

    static ResponseEntity<Resp> userNotExist() {
        return failureResp("User not exist", HttpStatus.BAD_REQUEST)
    }

    static ResponseEntity<Resp> categoryNotExist() {
        return failureResp("Category not exist!", HttpStatus.BAD_REQUEST)
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
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
            this.exception = exception;
            this
        }
    }
}

package com.pauldaniv.spark.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String target) {
        super(String.format("%s was not found", target));
    }
}

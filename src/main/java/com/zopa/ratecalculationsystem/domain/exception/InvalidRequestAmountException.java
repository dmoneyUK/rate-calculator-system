package com.zopa.ratecalculationsystem.domain.exception;

public class InvalidRequestAmountException extends RuntimeException {
    public InvalidRequestAmountException(String message) {
        super(message);
    }
}

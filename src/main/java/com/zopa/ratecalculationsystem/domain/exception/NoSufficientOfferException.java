package com.zopa.ratecalculationsystem.domain.exception;

public class NoSufficientOfferException extends RuntimeException {
    
    public NoSufficientOfferException(String message) {
        super(message);
    }
}

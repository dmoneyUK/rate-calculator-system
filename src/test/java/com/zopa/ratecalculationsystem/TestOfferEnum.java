package com.zopa.ratecalculationsystem;

import com.zopa.ratecalculationsystem.domain.model.Offer;

import java.math.BigDecimal;

public enum TestOfferEnum {
    
    Bob(new Offer("Bob", BigDecimal.valueOf(0.075), BigDecimal.valueOf(640))),
    Jane(new Offer("Jane", BigDecimal.valueOf(0.069), BigDecimal.valueOf(480))),
    Fred(new Offer("Fred", BigDecimal.valueOf(0.071), BigDecimal.valueOf(520))),
    Mary(new Offer("Mary", BigDecimal.valueOf(0.104), BigDecimal.valueOf(170))),
    John(new Offer("John", BigDecimal.valueOf(0.081), BigDecimal.valueOf(320))),
    Dave(new Offer("Dave", BigDecimal.valueOf(0.074), BigDecimal.valueOf(140))),
    Angela(new Offer("Angela", BigDecimal.valueOf(0.071), BigDecimal.valueOf(60))),
    Dan(new Offer("Dan", BigDecimal.valueOf(0.12), BigDecimal.valueOf(100)));
    
    private Offer offer;
    
    TestOfferEnum(Offer offer) {
        this.offer = offer;
    }
    
    public Offer offer() {
        return offer;
    }
}

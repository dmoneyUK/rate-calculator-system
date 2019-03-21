package com.zopa.ratecalculationsystem;

import com.zopa.ratecalculationsystem.domain.model.Offer;

public enum TestOfferEnum {
    
    Bob(new Offer("Bob", 0.075, 640)),
    Jane(new Offer("Jane", 0.069, 480)),
    Fred(new Offer("Fred", 0.071, 520)),
    Mary(new Offer("Mary", 0.104, 170)),
    John(new Offer("John", 0.081, 320)),
    Dave(new Offer("Dave", 0.074, 140)),
    Angela(new Offer("Angela", 0.071, 60));
    private Offer offer;
    
    TestOfferEnum(Offer offer) {
        this.offer = offer;
    }
    
    public Offer offer() {
        return offer;
    }
}

package com.zopa.ratecalculationsystem;

import com.zopa.ratecalculationsystem.domain.model.Offer;

public enum TestOfferEnum {
    
    Bob(Offer.builder().lender("Bob").rate(0.075).available(640).build()),
    Jane(Offer.builder().lender("Jane").rate(0.069).available(480).build()),
    Fred(Offer.builder().lender("Fred").rate(0.071).available(520).build()),
    Mary(Offer.builder().lender("Mary").rate(0.104).available(170).build()),
    John(Offer.builder().lender("John").rate(0.081).available(320).build()),
    Dave(Offer.builder().lender("Dave").rate(0.074).available(140).build()),
    Angela(Offer.builder().lender("Angela").rate(0.071).available(60).build());
    
    private Offer offer;
    
    TestOfferEnum(Offer offer) {
        this.offer = offer;
    }
    
    public Offer offer() {
        return offer;
    }
}

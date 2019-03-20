package com.zopa.ratecalculationsystem.service;

import com.zopa.ratecalculationsystem.model.Offer;

import java.math.BigDecimal;
import java.util.List;

public interface OfferService {
    
    List<Offer> getAvailableOffers();
    
    List<Offer> getLowInterestOffers(BigDecimal requestAmount);
}

package com.zopa.ratecalculationsystem.domain.service;

import com.zopa.ratecalculationsystem.domain.model.Offer;

import java.math.BigDecimal;
import java.util.List;

public interface OfferService {
    
    void loadOffers(String fileName);
    
    List<Offer> getLowInterestOffers(BigDecimal requestAmount);
    
    
}

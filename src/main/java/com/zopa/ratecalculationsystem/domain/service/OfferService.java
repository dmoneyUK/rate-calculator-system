package com.zopa.ratecalculationsystem.domain.service;

import com.zopa.ratecalculationsystem.domain.model.Offer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface OfferService {
    
    void loadOffers(String fileName) throws IOException;
    
    List<Offer> getLowInterestOffers(Integer requestAmount);
    
    
}

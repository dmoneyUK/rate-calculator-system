package com.zopa.ratecalculationsystem.service;

import java.util.List;

public interface OfferLoader {
    
    List<Offer> loadOffers(String fileName);
}

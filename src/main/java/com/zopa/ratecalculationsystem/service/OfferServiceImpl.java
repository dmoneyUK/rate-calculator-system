package com.zopa.ratecalculationsystem.service;

import com.zopa.ratecalculationsystem.model.Offer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

public class OfferServiceImpl implements OfferService {
    
    private List<Offer> availableOffers;
    private CsvLoader offerLoader;
    
    public OfferServiceImpl(CsvLoader offerLoader) {
        availableOffers = new ArrayList<>();
        this.offerLoader = offerLoader;
    }
    
    @Override
    public List<Offer> getAvailableOffers() {
        if (availableOffers.isEmpty()) {
            availableOffers = offerLoader.load(Offer.class, null);
        }
        return availableOffers;
    }
    
    @Override
    public List<Offer> getLowInterestOffers(BigDecimal requestAmount) {
        List<Offer> availableOffers = getAvailableOffers();
        availableOffers.sort(comparing(Offer::getRate));
        
        return selectLowInterestOffers(availableOffers, requestAmount);
    }
    
    private List<Offer> selectLowInterestOffers(List<Offer> availableOffers, BigDecimal requestAmount) {
        List<Offer> selected = new ArrayList<>();
        for (Offer offer : availableOffers) {
            if (requestAmount.compareTo(offer.getAvailable()) > 0) {
                selected.add(offer);
            } else if (requestAmount.compareTo(offer.getAvailable()) == 0) {
                selected.add(offer);
                break;
            } else {
                selected.add(new Offer(offer.getLender(), offer.getRate(), requestAmount));
                break;
            }
            requestAmount = requestAmount.subtract(offer.getAvailable());
        }
        return selected;
    }
}

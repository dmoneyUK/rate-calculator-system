package com.zopa.ratecalculationsystem.domain.service;

import com.zopa.ratecalculationsystem.domain.model.Offer;
import com.zopa.ratecalculationsystem.infrastructure.CsvLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Comparator.comparing;

@Service
@Slf4j
public class OfferServiceImpl implements OfferService {
    
    private List<Offer> availableOffers;
    private CsvLoader offerLoader;
    
    @Autowired
    public OfferServiceImpl(CsvLoader offerLoader) {
        availableOffers = new ArrayList<>();
        this.offerLoader = offerLoader;
    }
    
    @Override
    public void loadOffers(String fileName) throws IOException {
        this.availableOffers = offerLoader.load(Offer.class, fileName);
        availableOffers.sort(comparing(Offer::getRate).thenComparing(Offer::getAvailable));
    }
    
    @Override
    public List<Offer> getLowInterestOffers(BigDecimal requestAmount) {
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
        if(selected.isEmpty()){
            System.out.println("Cannot find sufficient offers at that time.");
        }
        return selected;
    }
    
}

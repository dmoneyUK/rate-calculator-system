package com.zopa.ratecalculationsystem.domain.service;

import com.zopa.ratecalculationsystem.domain.exception.NoSufficientOfferException;
import com.zopa.ratecalculationsystem.domain.model.Offer;
import com.zopa.ratecalculationsystem.infrastructure.CsvLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
    public List<Offer> getLowInterestOffers(Integer requestAmount) {
        
        List<Offer> selected = new ArrayList<>();
    
        int remaining = requestAmount;
        Iterator<Offer> it = availableOffers.iterator();
        while (it.hasNext() && remaining != 0) {
            Offer offer = it.next();
            if (remaining >= offer.getAvailable()) {
                selected.add(offer);
                remaining -= offer.getAvailable();
            } else {
                selected.add(Offer.builder()
                                  .lender(offer.getLender())
                                  .rate(offer.getRate())
                                  .available(remaining).build());
                remaining=0;
            }
        }
    
        if (remaining != 0) {
            throw new NoSufficientOfferException("Cannot find sufficient offers at that time.");
        }
        return selected;
    }
    
}

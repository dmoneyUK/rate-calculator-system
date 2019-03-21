package com.zopa.ratecalculationsystem.domain.service;

import com.zopa.ratecalculationsystem.domain.model.Loan;
import com.zopa.ratecalculationsystem.domain.model.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

@Service
public class LoanServiceImpl implements LoanService {
    
    public static final int TERM = 36;
    public static final int MONTHS_A_YEAR = 12;
    
    private OfferService offerService;
    
    @Autowired
    public LoanServiceImpl(OfferService offerService) {
        this.offerService = offerService;
    }
    
    @Override
    public Loan getLoan(BigDecimal requestAmount) {
        List<Offer> selectedOffers = offerService.getLowInterestOffers(requestAmount);
        
        return buildLoan(requestAmount, selectedOffers);
    }
    
    private Loan buildLoan(BigDecimal requestAmount, List<Offer> offers) {
        BigDecimal totalRepayment = calculateTotalRepayment(offers);
        BigDecimal rate = calculateRate(offers);
        
        Loan result = new Loan();
        result.setRequestAmount(requestAmount);
        result.setTotalRepayment(totalRepayment);
        result.setMonthlyRepayment(totalRepayment.divide(valueOf(TERM), 2, HALF_UP));
        result.setRate(rate);
        return result;
    }
    
    private BigDecimal calculateRate(List<Offer> offers) {
        return offers.stream()
                     .map(offer -> offer.getRate())
                     .reduce(BigDecimal.ZERO, (a, b) -> a.add(b)).divide(BigDecimal.valueOf(offers.size()), 3, HALF_UP);
    }
    
    private BigDecimal calculateTotalRepayment(List<Offer> offers) {
        return offers.stream()
                     .map(offer -> offer.getRate()
                                        .divide(valueOf(MONTHS_A_YEAR))
                                        .add(ONE)
                                        .pow(TERM)
                                        .multiply(offer.getAvailable()).setScale(2, HALF_UP))
                     .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }
    
}

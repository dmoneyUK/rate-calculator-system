package com.zopa.ratecalculationsystem.service.impl;

import com.zopa.ratecalculationsystem.model.Loan;
import com.zopa.ratecalculationsystem.model.Offer;
import com.zopa.ratecalculationsystem.service.LoanService;
import com.zopa.ratecalculationsystem.service.OfferService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.function.Function;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

public class LoanServiceImpl implements LoanService {
    
    private OfferService offerService;
    
    public LoanServiceImpl(OfferService offerService) {
        this.offerService = offerService;
    }
    
    @Override
    public Loan getLoan(BigDecimal requestAmount) {
        List<Offer> selectedOffers = offerService.getLowInterestOffers(requestAmount);
        
        Function<List<Offer>, Loan> buildLoan = offers -> {
            Loan result = new Loan();
            result.setRequestAmount(requestAmount);
            BigDecimal totalRepayment = offers.stream()
                                              .map(offer -> offer.getRate()
                                                                 .divide(valueOf(12))
                                                                 .add(ONE)
                                                                 .pow(36)
                                                                 .multiply(offer.getAvailable()).setScale(2,HALF_UP))
                                              .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
            BigDecimal rate = offers.stream()
                                    .map(offer -> offer.getRate())
                                    .reduce(BigDecimal.ZERO, (a, b) -> a.add(b)).divide(BigDecimal.valueOf(offers.size()),3, HALF_UP);
            result.setTotalRepayment(totalRepayment);
            result.setMonthlyRepayment(totalRepayment.divide(valueOf(36),2,HALF_UP));
            result.setRate(rate);
            return result;
        };
        return buildLoan.apply(selectedOffers);
    }
    
}

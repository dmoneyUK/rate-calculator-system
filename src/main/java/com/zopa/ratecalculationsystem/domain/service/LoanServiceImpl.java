package com.zopa.ratecalculationsystem.domain.service;

import com.zopa.ratecalculationsystem.domain.model.Loan;
import com.zopa.ratecalculationsystem.domain.model.Offer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

@Service
@Slf4j
public class LoanServiceImpl implements LoanService {
    
    public static final int TERM = 36;
    public static final int MONTHS_A_YEAR = 12;
    
    private OfferService offerService;
    
    @Autowired
    public LoanServiceImpl(OfferService offerService) {
        this.offerService = offerService;
    }
    
    @Override
    public Optional<Loan> getLoan(BigDecimal requestAmount) {
    
        if (!validateRequestAmount(requestAmount)) {
            return Optional.empty();
        }
        
        List<Offer> selectedOffers = offerService.getLowInterestOffers(requestAmount);
    
        return Optional.of(buildLoan(requestAmount, selectedOffers));
    }
    
    private boolean validateRequestAmount(BigDecimal requestAmount) {
        if (requestAmount.compareTo(BigDecimal.valueOf(1000)) < 0
                || requestAmount.compareTo(BigDecimal.valueOf(15000)) > 0
                || requestAmount.remainder(BigDecimal.valueOf(100)).compareTo(BigDecimal.ZERO) != 0) {
            System.out.println("Request amount must be any £100 increment between £1000 and £15000 inclusive");
            return false;
        }
        return true;
    }
    
    private Loan buildLoan(BigDecimal requestAmount, List<Offer> offers) {
        BigDecimal totalRepayment = calculateTotalRepayment(offers);
        BigDecimal rate = calculateRate(offers);
        
        Loan result = new Loan();
        result.setRequestAmount(requestAmount);
        result.setTotalRepayment(totalRepayment);
        result.setMonthlyRepayment(calculateMonthlyPayment(totalRepayment));
        result.setRate(rate);
        return result;
    }
    
    private BigDecimal calculateTotalRepayment(List<Offer> offers) {
        return offers.stream()
                     .map(offer -> offer.getRate()
                                        .divide(valueOf(MONTHS_A_YEAR),2, HALF_UP)
                                        .add(ONE)
                                        .pow(TERM)
                                        .multiply(offer.getAvailable()).setScale(2, HALF_UP))
                     .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }
    
    private BigDecimal calculateMonthlyPayment(BigDecimal totalRepayment) {
        return totalRepayment.divide(valueOf(TERM), 2, HALF_UP);
    }
    
    private BigDecimal calculateRate(List<Offer> offers) {
        return offers.stream()
                     .map(offer -> offer.getRate())
                     .reduce(BigDecimal.ZERO, (a, b) -> a.add(b)).divide(BigDecimal.valueOf(offers.size()), 3, HALF_UP);
    }
    
}

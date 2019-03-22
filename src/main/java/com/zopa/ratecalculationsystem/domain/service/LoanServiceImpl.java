package com.zopa.ratecalculationsystem.domain.service;

import com.zopa.ratecalculationsystem.domain.exception.InvalidRequestAmountException;
import com.zopa.ratecalculationsystem.domain.model.Loan;
import com.zopa.ratecalculationsystem.domain.model.Offer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zopa.ratecalculationsystem.infrastructure.DoubleFormatter.SCALE_ONE;
import static com.zopa.ratecalculationsystem.infrastructure.DoubleFormatter.SCALE_TWO;
import static com.zopa.ratecalculationsystem.infrastructure.DoubleFormatter.format;

@Service
@Slf4j
public class LoanServiceImpl implements LoanService {
    
    public static final Integer LOAN_TERM = 36;
    public static final Integer MONTHS_A_YEAR = 12;
    public static final Integer MINI_REQUEST_AMOUNT = 1000;
    public static final Integer MAX_REQUEST_AMOUNT = 15000;
    public static final Integer INCREMENT = 100;
    
    private OfferService offerService;
    
    @Autowired
    public LoanServiceImpl(OfferService offerService) {
        this.offerService = offerService;
    }
    
    @Override
    public Loan getLoan(Integer requestAmount) {
        validateRequestAmount(requestAmount);
        
        List<Offer> selectedOffers = offerService.getLowInterestOffers(requestAmount);
    
        return createLoan(requestAmount, selectedOffers);
    }
    
    private void validateRequestAmount(Integer requestAmount) {
        if (requestAmount < MINI_REQUEST_AMOUNT
                || requestAmount > MAX_REQUEST_AMOUNT
                || requestAmount % INCREMENT != 0) {
            throw new InvalidRequestAmountException("Request amount must be any £100 increment between £1000 and £15000 inclusive");
        }
    }
    
    private Loan createLoan(Integer requestAmount, List<Offer> offers) {
        
        Double apr = getApr(requestAmount, offers);
        Double effectiveMonthlyInterestRate = getEffectiveMonthlyInterestRate(apr);
        Double monthlyRepayment = getMonthlyRepayment(requestAmount, effectiveMonthlyInterestRate);
        Double totalRepayment = monthlyRepayment * LOAN_TERM;
        return Loan.builder()
                   .requestAmount(requestAmount)
                   .totalRepayment(format(totalRepayment, SCALE_TWO))
                   .monthlyRepayment(format(monthlyRepayment, SCALE_TWO))
                   .rate(format(apr * 100, SCALE_ONE)).build();
        
    }
    
    private Double getApr(Integer loanAmount, List<Offer> offers) {
        Double annualInterest = offers.stream()
                                      .map(offer -> offer.getAvailable() * offer.getRate())
                                      .reduce(0.0, (a, b) -> a + b);
        return annualInterest / loanAmount;
    }
    
    private Double getEffectiveMonthlyInterestRate(Double apr) {
        return Math.pow((1 + apr), (1.0 / MONTHS_A_YEAR)) - 1;
    }
    
    private Double getMonthlyRepayment(Integer loanAmount, Double effectiveMonthlyInterestRate) {
        return effectiveMonthlyInterestRate * loanAmount / (1 - (Math.pow(1 + effectiveMonthlyInterestRate, -LOAN_TERM)));
    }
    
}

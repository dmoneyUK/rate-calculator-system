package com.zopa.ratecalculationsystem;

import com.zopa.ratecalculationsystem.domain.service.LoanService;
import com.zopa.ratecalculationsystem.domain.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class RateCalculationRunner implements CommandLineRunner {
    
    private final OfferService offerService;
    private final LoanService loanService;
    
    @Autowired
    public RateCalculationRunner(LoanService loanService, OfferService offerService) {
        this.loanService = loanService;
        this.offerService = offerService;
        
    }
    
    @Override
    public void run(String... args) throws Exception {
        log.info("RateCalculationRunner started");
        offerService.loadOffers(args[0]);
        loanService.getLoan(BigDecimal.valueOf(Integer.valueOf(args[1])));
    }
}

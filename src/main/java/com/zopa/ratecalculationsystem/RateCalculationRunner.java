package com.zopa.ratecalculationsystem;

import com.zopa.ratecalculationsystem.domain.model.Loan;
import com.zopa.ratecalculationsystem.domain.service.LoanService;
import com.zopa.ratecalculationsystem.domain.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
        try {
            Loan loan = loanService.getLoan(Integer.valueOf(args[1]));
            System.out.println("Requested amount: £" + loan.getRequestAmount());
            System.out.println("Rate: "+loan.getRate()+"%");
            System.out.println("Monthly repayment: £"+loan.getMonthlyRepayment());
            System.out.println("Total repayment: £"+loan.getTotalRepayment());
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}

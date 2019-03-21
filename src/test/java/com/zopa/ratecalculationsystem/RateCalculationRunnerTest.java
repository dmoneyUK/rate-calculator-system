package com.zopa.ratecalculationsystem;

import com.zopa.ratecalculationsystem.domain.service.LoanService;
import com.zopa.ratecalculationsystem.domain.service.OfferService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RateCalculationRunnerTest {
    
    private RateCalculationRunner testObj;
    
    @Mock
    private LoanService loanServiceMock;
    
    @Mock
    private OfferService offerServiceMock;
    
    @Before
    public void setUp(){
        testObj = new RateCalculationRunner(loanServiceMock, offerServiceMock);
    }
    
    @Test
    public void shouldLoadOffers() throws Exception {
    
        testObj.run("TestFile", "1000");
        verify(offerServiceMock).loadOffers("TestFile");
        verify(loanServiceMock).getLoan(BigDecimal.valueOf(1000));
    }
}

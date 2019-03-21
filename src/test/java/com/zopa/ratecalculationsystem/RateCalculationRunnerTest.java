package com.zopa.ratecalculationsystem;

import com.zopa.ratecalculationsystem.domain.model.Loan;
import com.zopa.ratecalculationsystem.domain.service.LoanService;
import com.zopa.ratecalculationsystem.domain.service.OfferService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    
        //given
        when(loanServiceMock.getLoan(anyInt())).thenReturn(mock(Loan.class));
    
        testObj.run("TestFile", "1000");
        verify(offerServiceMock).loadOffers("TestFile");
        verify(loanServiceMock).getLoan(1000);
    }
}

package com.zopa.ratecalculationsystem.domain.service.impl;

import com.zopa.ratecalculationsystem.domain.exception.InvalidRequestAmountException;
import com.zopa.ratecalculationsystem.domain.model.Loan;
import com.zopa.ratecalculationsystem.domain.service.LoanServiceImpl;
import com.zopa.ratecalculationsystem.domain.service.OfferService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.zopa.ratecalculationsystem.TestOfferEnum.Fred;
import static com.zopa.ratecalculationsystem.TestOfferEnum.Jane;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceImplUTest {
    
    private LoanServiceImpl testObj;
    
    @Mock OfferService offerServiceMock;
    
    @Before
    public void setup() {
        testObj = new LoanServiceImpl(offerServiceMock);
    }
    
    @Test
    public void shouldReturnLoanWithLowInterestOffers() {
        
        //given
        int requestAmount = 1000;
        when(offerServiceMock.getLowInterestOffers(requestAmount))
                .thenReturn(newArrayList(Jane.offer(), Fred.offer()));
        
        //when
        Loan actual = testObj.getLoan(requestAmount);
        
        //then
        verify(offerServiceMock).getLowInterestOffers(requestAmount);
        assertThat(actual.getRequestAmount()).isEqualTo(requestAmount);
        assertThat(actual.getTotalRepayment().toString()).isEqualTo("1108.10");
        assertThat(actual.getMonthlyRepayment().toString()).isEqualTo("30.78");
        assertThat(actual.getRate().toString()).isEqualTo("7.0");
        
        
    }
    
    @Test(expected = InvalidRequestAmountException.class)
    public void shouldReturnAbsentWhenRequestAmountLessThan1000() {
        
        //when
        Loan actual = testObj.getLoan(10);
        
        //then
        verify(offerServiceMock, never()).getLowInterestOffers(anyInt());
        
    }
    
    @Test(expected = InvalidRequestAmountException.class)
    public void shouldReturnAbsentWhenRequestAmountOver15000() {
        
        //when
        Loan actual = testObj.getLoan(16000);
        
        //then
        verify(offerServiceMock, never()).getLowInterestOffers(anyInt());
        
    }
    
    @Test(expected = InvalidRequestAmountException.class)
    public void shouldReturnAbsentWhenRequestAmountIsNotTimesOf100() {
    
        //when
        testObj.getLoan(1001);
    
    }
}

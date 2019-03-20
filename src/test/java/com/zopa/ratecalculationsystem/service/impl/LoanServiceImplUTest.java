package com.zopa.ratecalculationsystem.service.impl;

import com.zopa.ratecalculationsystem.model.Loan;
import com.zopa.ratecalculationsystem.service.OfferService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static com.zopa.ratecalculationsystem.TestOfferEnum.Dan;
import static com.zopa.ratecalculationsystem.TestOfferEnum.Jane;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.Matchers.any;
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
        
        BigDecimal expectedAmount = valueOf(100);
        //Given
        when(offerServiceMock.getLowInterestOffers(expectedAmount))
                .thenReturn(newArrayList(Dan.offer()));
        
        //when
        Loan actual = testObj.getLoan(expectedAmount);
        
        //then
        verify(offerServiceMock).getLowInterestOffers(expectedAmount);
        assertThat(actual.getRequestAmount()).isEqualTo(expectedAmount);
        assertThat(actual.getTotalRepayment()).isEqualTo(valueOf(119.67));
        assertThat(actual.getMonthlyRepayment()).isEqualTo(valueOf(3.32));
        assertThat(actual.getRate()).isEqualTo(valueOf(0.060).setScale(3));
        
        
    }
    
}

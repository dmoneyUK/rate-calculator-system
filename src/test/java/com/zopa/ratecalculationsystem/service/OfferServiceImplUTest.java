package com.zopa.ratecalculationsystem.service;

import com.zopa.ratecalculationsystem.TestOfferEnum;
import com.zopa.ratecalculationsystem.model.Offer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.zopa.ratecalculationsystem.TestOfferEnum.Angela;
import static com.zopa.ratecalculationsystem.TestOfferEnum.Fred;
import static com.zopa.ratecalculationsystem.TestOfferEnum.Jane;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OfferServiceImplUTest {
    
    private OfferServiceImpl offerServiceImpl;
    
    @Mock private CsvLoader csvLoaderMock;
    
    private List testOffers;
    
    @Before
    public void setUp() {
        offerServiceImpl = new OfferServiceImpl(csvLoaderMock);
        testOffers = Arrays.stream(TestOfferEnum.values())
                           .map(e -> e.offer())
                           .collect(Collectors.toList());
        when(csvLoaderMock.load(eq(Offer.class), anyString())).thenReturn(testOffers);
    }
    
    @Test
    public void shouldLoadfromCsvWhenAvailableOffersIsEmpty() {
        
        //when
        List<Offer> actual = offerServiceImpl.getAvailableOffers();
        
        //then
        assertThat(actual).isNotNull();
        verify(csvLoaderMock).load(eq(Offer.class), anyString());
        assertThat(actual).isEqualTo(testOffers);
    }
    
    @Test
    public void shouldReturnOneOfferOfLowRate() {
        //when
        List<Offer> actual = offerServiceImpl.getLowInterestOffers(Jane.offer()
                                                                       .getAvailable());
        
        //then
        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0)).isEqualTo(Jane.offer());
        
    }
    
    @Test
    public void shouldReturnOfferBundleOfLowRate() {
        //when
        List<Offer> actual = offerServiceImpl.getLowInterestOffers(Jane.offer().getAvailable()
                                                                       .add(Fred.offer().getAvailable()));
        
        //then
        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0)).isEqualTo(Jane.offer());
        assertThat(actual.get(1)).isEqualTo(Fred.offer());
        
    }
    
    @Test
    public void shouldReturnOfferBundleWithAPartilOfferOfLowRate() {
        //when
        List<Offer> actual = offerServiceImpl.getLowInterestOffers(Jane.offer().getAvailable()
                                                                       .add(Fred.offer().getAvailable())
                                                                       .add(BigDecimal.TEN));
        
        //then
        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(3);
        assertThat(actual.get(0)).isEqualTo(Jane.offer());
        assertThat(actual.get(1)).isEqualTo(Fred.offer());
        Offer partialOffer = actual.get(2);
        assertThat(partialOffer.getLender()).isEqualTo(Angela.offer().getLender());
        assertThat(partialOffer.getRate()).isEqualTo(Angela.offer().getRate());
        assertThat(partialOffer.getAvailable()).isEqualTo(BigDecimal.TEN);
        
    }
    
}

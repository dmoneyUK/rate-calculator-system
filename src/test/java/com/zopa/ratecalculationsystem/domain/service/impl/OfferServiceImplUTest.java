package com.zopa.ratecalculationsystem.domain.service.impl;

import com.zopa.ratecalculationsystem.TestOfferEnum;
import com.zopa.ratecalculationsystem.domain.exception.NoSufficientOfferException;
import com.zopa.ratecalculationsystem.domain.model.Offer;
import com.zopa.ratecalculationsystem.domain.service.OfferServiceImpl;
import com.zopa.ratecalculationsystem.infrastructure.CsvLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.zopa.ratecalculationsystem.TestOfferEnum.Angela;
import static com.zopa.ratecalculationsystem.TestOfferEnum.Fred;
import static com.zopa.ratecalculationsystem.TestOfferEnum.Jane;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OfferServiceImplUTest {
    
    public static final String TEST_FILE_NAME = "file-name";
    private OfferServiceImpl testObj;
    
    @Mock private CsvLoader csvLoaderMock;
    
    private List testOffers;
    
    @Before
    public void setUp() throws IOException {
        testObj = new OfferServiceImpl(csvLoaderMock);
        testOffers = Arrays.stream(TestOfferEnum.values())
                           .map(e -> e.offer())
                           .collect(Collectors.toList());
        when(csvLoaderMock.load(Offer.class, TEST_FILE_NAME)).thenReturn(testOffers);
    }
    
    @Test
    public void shouldLoadfromCsv() throws IOException {
        
        //when
        testObj.loadOffers(TEST_FILE_NAME);
        
        //then
        verify(csvLoaderMock).load(Offer.class, TEST_FILE_NAME);
    }
    
    @Test
    public void shouldReturnOneOfferOfLowRate() throws IOException {
    
        //given
        testObj.loadOffers(TEST_FILE_NAME);
    
        //when
        List<Offer> actual = testObj.getLowInterestOffers(Jane.offer().getAvailable());
        
        //then
        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0)).isEqualTo(Jane.offer());
        
    }
    
    @Test
    public void shouldReturnOfferBundleOfLowRate() throws IOException {
    
        //given
        testObj.loadOffers(TEST_FILE_NAME);
        
        //when
        List<Offer> actual = testObj.getLowInterestOffers(Jane.offer().getAvailable()
                                                                  + Angela.offer().getAvailable());
        
        //then
        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual.get(0)).isEqualTo(Jane.offer());
        assertThat(actual.get(1)).isEqualTo(Angela.offer());
        
    }
    
    @Test
    public void shouldReturnOfferBundleWithAPartilOfferOfLowRate() throws IOException {
        //given
        testObj.loadOffers(TEST_FILE_NAME);
        
        //when
        List<Offer> actual = testObj.getLowInterestOffers(Jane.offer().getAvailable()
                                                                  + Angela.offer().getAvailable()
                                                                  + 10);
        
        //then
        assertThat(actual).isNotNull();
        assertThat(actual.size()).isEqualTo(3);
        
        assertThat(actual.get(0)).isEqualTo(Jane.offer());
        assertThat(actual.get(1)).isEqualTo(Angela.offer());
        
        Offer partialOffer = actual.get(2);
        assertThat(partialOffer.getLender()).isEqualTo(Fred.offer().getLender());
        assertThat(partialOffer.getRate()).isEqualTo(Fred.offer().getRate());
        assertThat(partialOffer.getAvailable()).isEqualTo(10);
        
    }
    
    @Test(expected = NoSufficientOfferException.class)
    public void shouldReturnEmptyListWhenCannotFindSufficientOffers() {
        
        //when
        List<Offer> actual = testObj.getLowInterestOffers(Jane.offer().getAvailable());
        
        //then
        assertThat(actual.size()).isEqualTo(0);
    }
    
}

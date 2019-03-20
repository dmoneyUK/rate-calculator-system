package com.zopa.ratecalculationsystem.service.impl;

import com.zopa.ratecalculationsystem.TestOfferEnum;
import com.zopa.ratecalculationsystem.model.Offer;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Ideally, should have more test. Considering the time, just created one to verify the csv file can be loaded and covert to offers.
 */
public class OfferCsvLoaderImplUTest {
    private OfferCsvLoaderImpl testObj;
    
    @Before
    public void setUp() {
        testObj = new OfferCsvLoaderImpl();
    }
    
    @Test
    public void shouldLoadCsvToOfferList() {
        List<Offer> actual = testObj.load(Offer.class, "test_market_data.csv");
        assertThat(actual.size()).isEqualTo(7);
        Arrays.stream(TestOfferEnum.values())
              .forEach(e -> {
                  actual.contains(e.offer());
              });
    }
    
    @Test
    public void shouldUseDefaultFileLoadCsvToOfferList() {
        List<Offer> actual = testObj.load(Offer.class, "");
        assertThat(actual.size()).isEqualTo(7);
        Arrays.stream(TestOfferEnum.values())
              .forEach(e -> {
                  actual.contains(e.offer());
              });
    }
    
    @Test
    public void shouldUseDefaultFileIsNull() {
        List<Offer> actual = testObj.load(Offer.class, null);
        assertThat(actual.size()).isEqualTo(7);
        Arrays.stream(TestOfferEnum.values())
              .forEach(e -> {
                  actual.contains(e.offer());
              });
    }
}

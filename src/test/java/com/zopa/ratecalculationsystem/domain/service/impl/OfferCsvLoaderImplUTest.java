package com.zopa.ratecalculationsystem.domain.service.impl;

import com.zopa.ratecalculationsystem.TestOfferEnum;
import com.zopa.ratecalculationsystem.infrastructure.OfferCsvLoaderImpl;
import com.zopa.ratecalculationsystem.domain.model.Offer;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


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
}

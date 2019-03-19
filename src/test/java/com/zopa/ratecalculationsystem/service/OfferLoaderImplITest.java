package com.zopa.ratecalculationsystem.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import sun.security.util.Resources_sv;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class OfferLoaderImplITest {
    private OfferLoaderImpl csvLoader;
    
    @Before
    public void setUp() {
        csvLoader = new OfferLoaderImpl();
    }
    
    @Test
    public void shouldLoadCsvToList() {
        List<Offer> actual = csvLoader.loadOffers("test_market_data.csv");
        
        assertThat(actual.size()).isEqualTo(7);
    }
}

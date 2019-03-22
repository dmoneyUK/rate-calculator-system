package com.zopa.ratecalculationsystem.infrastructure;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class DoubleFormatterUTest {
    
    public static double TEST_VALUE = 0.1000;
    
    @Test
    public void shouldFormatToScale1() {
    
        BigDecimal actual = DoubleFormatter.format(TEST_VALUE, DoubleFormatter.SCALE_ONE);
    
        assertThat(actual.toString()).isEqualTo("0.1");
    }
    
    @Test
    public void shouldFormatToScale2() {
    
        BigDecimal actual = DoubleFormatter.format(TEST_VALUE, DoubleFormatter.SCALE_TWO);
    
        assertThat(actual.toString()).isEqualTo("0.10");
    }
}

package com.zopa.ratecalculationsystem.infrastructure;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DoubleFormatterUTest {
    
    public static double TEST_VALUE = 0.1111;;
    
    @Test
    public void shouldFormatToScale3() {
    
        Double actual = DoubleFormatter.format(TEST_VALUE, DoubleFormatter.SCALE_THREE);
        
        assertThat(actual).isEqualTo(0.111);
    }
    
    @Test
    public void shouldFormatToScale2() {
        
        Double actual = DoubleFormatter.format(TEST_VALUE, DoubleFormatter.SCALE_TWO);
        
        assertThat(actual).isEqualTo(0.11);
    }
}

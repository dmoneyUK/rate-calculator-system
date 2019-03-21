package com.zopa.ratecalculationsystem.infrastructure;

import java.math.BigDecimal;
import java.util.function.Function;

public class DoubleFormatter {
    
    public static Function<Double, Double> SCALE_TWO = original -> BigDecimal.valueOf(original)
                                                                             .setScale(2, BigDecimal.ROUND_HALF_UP)
                                                                             .doubleValue();
    public static Function<Double, Double> SCALE_THREE = original -> BigDecimal.valueOf(original)
                                                                               .setScale(3, BigDecimal.ROUND_HALF_UP)
                                                                               .doubleValue();
    
    public static Double format(Double original, Function<Double, Double> formatFn) {
        return formatFn.apply(original);
    }
}

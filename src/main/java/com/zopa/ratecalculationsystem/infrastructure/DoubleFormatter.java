package com.zopa.ratecalculationsystem.infrastructure;

import java.math.BigDecimal;
import java.util.function.Function;

public class DoubleFormatter {
    
    public static Function<Double, BigDecimal> SCALE_ONE = original -> BigDecimal.valueOf(original)
                                                                                 .setScale(1, BigDecimal.ROUND_HALF_UP);
    public static Function<Double, BigDecimal> SCALE_TWO = original -> BigDecimal.valueOf(original)
                                                                             .setScale(2, BigDecimal.ROUND_HALF_UP);
    
    public static BigDecimal format(Double original, Function<Double, BigDecimal> formatFn) {
        return formatFn.apply(original);
    }
}

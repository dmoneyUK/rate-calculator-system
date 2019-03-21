package com.zopa.ratecalculationsystem.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
@EqualsAndHashCode
public class Loan {
    
    private BigDecimal requestAmount;
    private BigDecimal totalRepayment;
    private BigDecimal monthlyRepayment;
    private BigDecimal rate;
}

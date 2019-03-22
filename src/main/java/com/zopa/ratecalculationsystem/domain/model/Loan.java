package com.zopa.ratecalculationsystem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    
    private Integer requestAmount;
    private BigDecimal totalRepayment;
    private BigDecimal monthlyRepayment;
    private BigDecimal rate;
}

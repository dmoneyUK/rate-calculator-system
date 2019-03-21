package com.zopa.ratecalculationsystem.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    
    private Integer requestAmount;
    private Double totalRepayment;
    private Double monthlyRepayment;
    private Double rate;
}

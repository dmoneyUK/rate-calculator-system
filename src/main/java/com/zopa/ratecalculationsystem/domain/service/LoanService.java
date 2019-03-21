package com.zopa.ratecalculationsystem.domain.service;

import com.zopa.ratecalculationsystem.domain.model.Loan;

import java.math.BigDecimal;


public interface LoanService {
     Loan getLoan(BigDecimal requestAmount);
}

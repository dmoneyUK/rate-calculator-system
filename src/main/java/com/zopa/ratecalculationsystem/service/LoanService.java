package com.zopa.ratecalculationsystem.service;

import com.zopa.ratecalculationsystem.model.Loan;

import java.math.BigDecimal;

public interface LoanService {
     Loan getLoan(BigDecimal requestAmount);
}

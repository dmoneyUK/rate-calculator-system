package com.zopa.ratecalculationsystem.domain.service;

import com.zopa.ratecalculationsystem.domain.model.Loan;

import java.math.BigDecimal;
import java.util.Optional;

public interface LoanService {
     Optional<Loan> getLoan(BigDecimal requestAmount);
}

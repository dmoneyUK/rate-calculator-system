package com.zopa.ratecalculationsystem.domain.service;

import com.zopa.ratecalculationsystem.domain.model.Loan;

public interface LoanService {
     Loan getLoan(Integer requestAmount);
}

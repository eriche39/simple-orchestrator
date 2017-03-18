/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.sample.context;

import com.github.ehe.simpleorchestrator.context.HistoryContext;
import com.github.ehe.simpleorchestrator.sample.entity.LoanApplication;

public interface LoanContext extends HistoryContext<String> {
    LoanApplication getApplication();
    int getCreditScore();
    int getRiskScore();
    void setLoanApproveStatus(boolean isApproved);
}

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

/**
 * Created by eric on 3/12/17.
 */
public interface LoanContext extends HistoryContext<String> {
    LoanApplication getApplication();
    int getCreditScore();
    int getRiskScore();
    void setLoanApproveStatus(boolean isApproved);
}

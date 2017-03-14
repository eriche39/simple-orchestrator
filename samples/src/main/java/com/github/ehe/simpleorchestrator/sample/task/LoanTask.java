/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.sample.task;

import com.github.ehe.simpleorchestrator.Task;
import com.github.ehe.simpleorchestrator.exception.OrchestratorException;
import com.github.ehe.simpleorchestrator.sample.context.LoanContext;
import com.github.ehe.simpleorchestrator.sample.entity.LoanApplication;
import org.springframework.stereotype.Component;

/**
 * Created by eric on 3/12/17.
 */
@Component
public class LoanTask implements Task<LoanContext> {
    @Override
    public void execute(LoanContext context) throws OrchestratorException {
        context.logHistory(this.getClass().getName());
        LoanApplication app = context.getLoanApplication();
        int creditScore = context.getCreditScore();
        if(creditScore>650 && app.getLoanAmount()/app.getSalary()<5)
            context.setLoanApproveStatus(true);
        else
            context.setLoanApproveStatus(false);
    }
}

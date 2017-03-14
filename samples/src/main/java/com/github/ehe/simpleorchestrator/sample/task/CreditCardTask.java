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
import com.github.ehe.simpleorchestrator.sample.context.CreditCardContext;
import com.github.ehe.simpleorchestrator.sample.entity.CardApplication;
import org.springframework.stereotype.Component;

/**
 * Created by eric on 3/12/17.
 */
@Component
public class CreditCardTask implements Task<CreditCardContext> {
    @Override
    public void execute(CreditCardContext context) throws OrchestratorException {
        context.logHistory(this.getClass().getName());
        CardApplication app = context.getCardApplication();
        int creditScore = context.getCreditScore();
        if(creditScore>550 && app.getOccupation().length()>0 && context.getRiskScore()<50)
            context.setCardApproveStatus(true);
        else
            context.setCardApproveStatus(false);
    }
}

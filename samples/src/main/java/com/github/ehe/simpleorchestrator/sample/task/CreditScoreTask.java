/*
 *
 *  * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *  *
 *  * This software is licensed under
 *  *
 *  * MIT license
 *  *
 *
 */

package com.github.ehe.simpleorchestrator.sample.task;

import com.github.ehe.simpleorchestrator.Task;
import com.github.ehe.simpleorchestrator.exception.OrchestratorException;
import com.github.ehe.simpleorchestrator.sample.context.CreditScoreContext;
import org.springframework.stereotype.Component;

@Component
public class CreditScoreTask implements Task<CreditScoreContext> {
    @Override
    public void execute(CreditScoreContext context) throws OrchestratorException {
        context.logHistory(this.getClass().getName());
        String ssn = context.getSsn();
        if(ssn != null && ssn.length()>0){
            context.setCreditScore((ssn.charAt(0)-'0')*100);
        } else
            context.setCreditScore(0);
    }
}

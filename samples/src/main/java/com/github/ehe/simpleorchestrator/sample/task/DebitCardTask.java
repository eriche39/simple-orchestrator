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
import com.github.ehe.simpleorchestrator.sample.context.DebitCardContext;
import com.github.ehe.simpleorchestrator.sample.entity.CardApplication;
import org.springframework.stereotype.Component;

@Component
public class DebitCardTask implements Task<DebitCardContext> {
    @Override
    public void execute(DebitCardContext context) throws OrchestratorException {
        context.logHistory(this.getClass().getName());
        CardApplication app = context.getApplication();
        if(app.getOccupation().length()>0)
            context.setCardApproveStatus(true);
        else
            context.setCardApproveStatus(false);
    }
}

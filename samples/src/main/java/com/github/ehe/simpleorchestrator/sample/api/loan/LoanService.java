/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.sample.api.loan;

import com.github.ehe.simpleorchestrator.Orchestrator;
import com.github.ehe.simpleorchestrator.impl.OrchestratorImpl;
import com.github.ehe.simpleorchestrator.sample.entity.AppliationResult;
import com.github.ehe.simpleorchestrator.sample.entity.LoanApplication;
import com.github.ehe.simpleorchestrator.sample.exception.ValidationException;
import com.github.ehe.simpleorchestrator.sample.task.AsyncCheckRiskTask;
import com.github.ehe.simpleorchestrator.sample.task.CreditScoreTask;
import com.github.ehe.simpleorchestrator.sample.task.LoanTask;
import com.github.ehe.simpleorchestrator.sample.task.ValidationTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Card (Credit/Debit) application service
 *
 * Orchestration:
 *
 * application --> CreditScoreTask -> AsyncCheckRiskTask -> LoanTask   ->O AppliationResult
 *
 */

@Component
@Path("/application/loan")
public class LoanService {

    @Autowired
    ValidationTask<LoanApplication> validationTask;

    @Autowired
    CreditScoreTask creditScoreTask;

    @Autowired
    AsyncCheckRiskTask asyncCheckRiskTask;

    @Autowired
    LoanTask loanTask;

    @Autowired
    Orchestrator<LoanOrchestratorConext> orchestrator;

    @Bean
    ValidationTask<LoanApplication> loanValidationTask(){
        return new ValidationTask<LoanApplication>();
    }

    @Bean
    Orchestrator<LoanOrchestratorConext> loanOrchestrator(){
        return new OrchestratorImpl<LoanOrchestratorConext>(
                validationTask,creditScoreTask, asyncCheckRiskTask, loanTask);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public AppliationResult applyloan(LoanApplication app) {
        LoanOrchestratorConext context = new LoanOrchestratorConext() ;
        context.init(app);
        try {
            orchestrator.execute(context);
        } catch (ValidationException e){
            return new AppliationResult(e.getErrors());
        }
        return new AppliationResult(context.getApplication().getPerson().getName(), context.isApproved(), context.getHistory());
    }

}
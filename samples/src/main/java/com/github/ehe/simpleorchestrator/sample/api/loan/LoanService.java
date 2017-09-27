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

package com.github.ehe.simpleorchestrator.sample.api.loan;

import com.github.ehe.simpleorchestrator.Orchestrator;
import com.github.ehe.simpleorchestrator.impl.OrchestratorImpl;
import com.github.ehe.simpleorchestrator.sample.entity.ApplicationResult;
import com.github.ehe.simpleorchestrator.sample.entity.LoanApplication;
import com.github.ehe.simpleorchestrator.sample.exception.ValidationException;
import com.github.ehe.simpleorchestrator.sample.task.AsyncCheckRiskTask;
import com.github.ehe.simpleorchestrator.sample.task.CreditScoreTask;
import com.github.ehe.simpleorchestrator.sample.task.LoanTask;
import com.github.ehe.simpleorchestrator.sample.task.ValidationTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
 * application --&gt; CreditScoreTask --&gt; AsyncCheckRiskTask --&gt; LoanTask   --&gt; O ApplicationResult
 *
 */

@Component
@Path("/application/loan")
public class LoanService {

    @Autowired
    private ValidationTask<LoanApplication> validationTask;

    @Autowired
    private CreditScoreTask creditScoreTask;

    @Autowired
    private AsyncCheckRiskTask asyncCheckRiskTask;

    @Autowired
    private LoanTask loanTask;

    @Autowired
    private Orchestrator<LoanOrchestratorContext> orchestrator;

    @Bean
    private Orchestrator<LoanOrchestratorContext> loanOrchestrator(){
        return new OrchestratorImpl<>(
                validationTask,creditScoreTask, asyncCheckRiskTask, loanTask);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ApplicationResult applyloan(LoanApplication app) {
        LoanOrchestratorContext context = new LoanOrchestratorContext() ;
        context.init(app);
        try {
            orchestrator.execute(context);
        } catch (ValidationException e){
            return new ApplicationResult(e.getErrors());
        }
        return new ApplicationResult(context.getApplication().getPerson().getName(), context.isApproved(), context.getHistory());
    }

}
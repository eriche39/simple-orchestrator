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
import com.github.ehe.simpleorchestrator.sample.task.AsyncCheckRiskTask;
import com.github.ehe.simpleorchestrator.sample.task.CreditScoreTask;
import com.github.ehe.simpleorchestrator.sample.task.LoanTask;
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
 * application --> CreditScoreTask -> AsyncCheckRiskTask -> LoanTask   ->O AppliationResult
 *
 */

@Component
@Path("/application/loan")
public class LoanService {

    @Autowired
    CreditScoreTask creditScoreTask;

    @Autowired
    AsyncCheckRiskTask asyncCheckRiskTask;

    @Autowired
    LoanTask loanTask;

    @Autowired
    Orchestrator<LoanOrchestratorConext> orchestrator;

    @Bean
    Orchestrator<LoanOrchestratorConext> loanOrchestrator(){
        return new OrchestratorImpl<LoanOrchestratorConext>(
                creditScoreTask, asyncCheckRiskTask, loanTask);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public AppliationResult applyloan(LoanApplication app) {
        LoanOrchestratorConext context = new LoanOrchestratorConext() ;
        context.init(app);
        orchestrator.execute(context);
        return new AppliationResult(context.getLoanApplication().getPerson().getName(), context.isApproved(), context.getHistory());
    }

}
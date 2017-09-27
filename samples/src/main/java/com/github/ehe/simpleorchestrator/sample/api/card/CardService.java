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

package com.github.ehe.simpleorchestrator.sample.api.card;

import com.github.ehe.simpleorchestrator.Orchestrator;
import com.github.ehe.simpleorchestrator.impl.Channel;
import com.github.ehe.simpleorchestrator.impl.OrchestratorImpl;
import com.github.ehe.simpleorchestrator.impl.Selector;
import com.github.ehe.simpleorchestrator.sample.context.AsyncCheckRiskContext;
import com.github.ehe.simpleorchestrator.sample.context.CreditCardContext;
import com.github.ehe.simpleorchestrator.sample.entity.ApplicationResult;
import com.github.ehe.simpleorchestrator.sample.entity.CardApplication;
import com.github.ehe.simpleorchestrator.sample.selector.CardSelector;
import com.github.ehe.simpleorchestrator.sample.task.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.EnumMap;

/**
 * Card (Credit/Debit) application service
 *
 * Orchestration:
 *
 *                                                   DebitCardTask --------------------------------------
 *                                                  /                                                     \
 * application --&gt; CheckCreditTask --&gt; cardSelector x                                                      O ApplicationResult
 *                                                  \                                                     /
 *                                                   riskCreditChannel[AsyncCheckRiskTask, CreditCardTask]
 *
 */
@Component
@Path("/application/card")
public class CardService {

    @Autowired
    private ValidationTask<CardApplication> validationTask;

    @Autowired
    private CreditScoreTask creditScoreTask;

    @Autowired
    private DebitCardTask debitCardTask;

    @Autowired
    private CreditCardTask creditCardTask;

    @Autowired
    private AsyncCheckRiskTask asyncCheckRiskTask;

    @Autowired
    private Channel<RiskCreditContext> riskCreditChannel;

    @Autowired
    private Selector<CardApplication.CardType, CardSelectorContext> cardSelector;

    @Autowired
    private Orchestrator<CardOrchestratorContext> orchestrator;

    @Bean
    private ValidationTask<CardApplication> cardValidationTask(){
        return new ValidationTask<>();
    }

    @Bean
    private Channel<RiskCreditContext> riskCreditChannel(){
        return new Channel<>(asyncCheckRiskTask, creditCardTask);
    }

    @Bean
    private Selector<CardApplication.CardType, CardSelectorContext> cardSelector(){
        return new CardSelector(new EnumMap(CardApplication.CardType.class){{
            put(CardApplication.CardType.Credit, riskCreditChannel);
            put(CardApplication.CardType.Debit, debitCardTask);
        }});
    }

    @Bean
    private Orchestrator<CardOrchestratorContext> cardOrchestrator(){
        return new OrchestratorImpl<>(creditScoreTask, cardSelector);
    }

    @PostConstruct
    public void init(){
        System.out.println("aaa");
    }

    private interface RiskCreditContext extends AsyncCheckRiskContext, CreditCardContext {}

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ApplicationResult applyCard(CardApplication app) {
        CardOrchestratorContext context = new CardOrchestratorContext() ;
        context.init(app);
        orchestrator.execute(context);
        return new ApplicationResult(context.getApplication().getPerson().getName(), context.isApproved(), context.getHistory());
    }
}
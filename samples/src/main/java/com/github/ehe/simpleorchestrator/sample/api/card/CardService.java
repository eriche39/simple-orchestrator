/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.sample.api.card;

import com.github.ehe.simpleorchestrator.Orchestrator;
import com.github.ehe.simpleorchestrator.impl.Channel;
import com.github.ehe.simpleorchestrator.impl.OrchestratorImpl;
import com.github.ehe.simpleorchestrator.impl.Selector;
import com.github.ehe.simpleorchestrator.sample.context.AsyncCheckRiskContext;
import com.github.ehe.simpleorchestrator.sample.context.CreditCardContext;
import com.github.ehe.simpleorchestrator.sample.entity.AppliationResult;
import com.github.ehe.simpleorchestrator.sample.entity.CardApplication;
import com.github.ehe.simpleorchestrator.sample.entity.LoanApplication;
import com.github.ehe.simpleorchestrator.sample.selector.CardSelector;
import com.github.ehe.simpleorchestrator.sample.task.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.EnumMap;

/**
 * Card (Credit/Debit) application service
 *
 * Orchestration:
 *
 *                                                   DebitCardTask --------------------------------------
 *                                                  /                                                     \
 * application --> CheckCreditTask -> cardSelector x                                                      O AppliationResult
 *                                                  \                                                     /
 *                                                   riskCreditChannel[AsyncCheckRiskTask, CreditCardTask]
 *
 */
@Component
@Path("/application/card")
public class CardService {

    @Autowired
    ValidationTask<CardApplication> validationTask;

    @Autowired
    CreditScoreTask creditScoreTask;

    @Autowired
    DebitCardTask debitCardTask;

    @Autowired
    CreditCardTask creditCardTask;

    @Autowired
    AsyncCheckRiskTask asyncCheckRiskTask;

    @Autowired
    Channel<RiskCreditContext> riskCreditChannel;

    @Autowired
    Selector<CardApplication.CardType, CardSelectorConext> cardSelector;

    @Autowired
    Orchestrator<CardOrchestratorConext> orchestrator;

    @Bean
    ValidationTask<CardApplication> cardValidationTask(){
        return new ValidationTask<CardApplication>();
    }

    @Bean
    Channel<RiskCreditContext> riskCreditChannel(){
        return new Channel<RiskCreditContext>(asyncCheckRiskTask, creditCardTask);
    }

    @Bean
    Selector<CardApplication.CardType, CardSelectorConext> cardSelector(){
        return new CardSelector(new EnumMap(CardApplication.CardType.class){{
            put(CardApplication.CardType.Credit, riskCreditChannel);
            put(CardApplication.CardType.Debit, debitCardTask);
        }});
    }

    @Bean
    Orchestrator<CardOrchestratorConext> cardOrchestrator(){
        return new OrchestratorImpl<CardOrchestratorConext>(creditScoreTask, cardSelector);
    }

    @PostConstruct
    public void init(){
        System.out.println("aaa");
    }

    static private interface RiskCreditContext extends AsyncCheckRiskContext, CreditCardContext {};

//    private final Channel<RiskCreditContext> riskCreditChannel =
//            new Channel<RiskCreditContext>(asyncCheckRiskTask, new CreditCardTask());

//    private final Selector<CardType, CardSelectorConext> cardSelector = new CardSelector(new EnumMap(CardType.class){{
//        put(CardType.Credit, riskCreditChannel);
//        put(CardType.Debit, debitCardTask);
//    }});

//    private Orchestrator<CardOrchestratorConext> orchestrator =
//            new OrchestratorImpl<CardOrchestratorConext>(
//                    new CreditScoreTask(), cardSelector);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public AppliationResult applyCard(CardApplication app) {
        CardOrchestratorConext context = new CardOrchestratorConext() ;
        context.init(app);
        orchestrator.execute(context);
        return new AppliationResult(context.getApplication().getPerson().getName(), context.isApproved(), context.getHistory());
    }
}
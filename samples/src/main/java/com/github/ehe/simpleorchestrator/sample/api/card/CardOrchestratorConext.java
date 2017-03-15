/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.sample.api.card;

import com.github.ehe.simpleorchestrator.exception.OrchestratorException;
import com.github.ehe.simpleorchestrator.sample.context.CreditScoreContext;
import com.github.ehe.simpleorchestrator.sample.context.CreditCardContext;
import com.github.ehe.simpleorchestrator.sample.context.DebitCardContext;
import com.github.ehe.simpleorchestrator.sample.context.ValidationContext;
import com.github.ehe.simpleorchestrator.sample.entity.CardApplication;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class CardOrchestratorConext implements CreditScoreContext, CreditCardContext, DebitCardContext, CardSelectorConext, ValidationContext<CardApplication> {
    private CardApplication cardApplication;
    private int creditScore;
    private boolean isApproved;
    private ConcurrentLinkedQueue<String> history = new ConcurrentLinkedQueue<>();
    private Future<Integer> riskScore;

    public void init(CardApplication cardApplication){
        this.cardApplication = cardApplication;
    }

    @Override
    public String getSsn() {
        return cardApplication.getPerson().getSsn();
    }

    @Override
    public void setRiskScore(Future<Integer> score) {
        this.riskScore = score;
    }

    @Override
    public void setCreditScore(int score) {
        this.creditScore = score;
    }

    @Override
    public CardApplication getApplication() {
        return this.cardApplication;
    }

    @Override
    public int getCreditScore() {
        return this.creditScore;
    }

    @Override
    public int getRiskScore() {
        try {
            return this.riskScore.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new OrchestratorException("asyncRiskTask Failed", e);
        } catch (ExecutionException e) {
            e.printStackTrace();
            throw new OrchestratorException("asyncRiskTask Failed", e);
        }
    }

    @Override
    public void setCardApproveStatus(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public List<String> getHistory() {
        return history.stream().collect(Collectors.toList());
    }

    @Override
    public void logHistory(String history) {
        this.history.add(history);
    }
}

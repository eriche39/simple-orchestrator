/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.sample.api.loan;

import com.github.ehe.simpleorchestrator.exception.OrchestratorException;
import com.github.ehe.simpleorchestrator.sample.context.AsyncCheckRiskContext;
import com.github.ehe.simpleorchestrator.sample.context.CreditScoreContext;
import com.github.ehe.simpleorchestrator.sample.context.LoanContext;
import com.github.ehe.simpleorchestrator.sample.entity.LoanApplication;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * Created by eric on 3/12/17.
 */
public class LoanOrchestratorConext implements CreditScoreContext, AsyncCheckRiskContext, LoanContext {
    private LoanApplication application;
    private int creditScore;
    private boolean isApproved;
    private ConcurrentLinkedQueue<String> history = new ConcurrentLinkedQueue<>();
    private Future<Integer> riskScore;

    public void init(LoanApplication application){
        this.application = application;
    }

    @Override
    public String getSsn() {
        return application.getPerson().getSsn();
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
    public LoanApplication getLoanApplication() {
        return this.application;
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
    public void setLoanApproveStatus(boolean isApproved) {
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

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
import com.github.ehe.simpleorchestrator.sample.context.AsyncCheckRiskContext;
import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;

@Component
public class AsyncCheckRiskTask implements Task<AsyncCheckRiskContext> {
    private static List<String> blackList = Lists.newArrayList("111222333", "999999999");
    @Override
    public void execute(AsyncCheckRiskContext context) throws OrchestratorException {
        context.logHistory(this.getClass().getName());
        String ssn = context.getSsn();
        context.setRiskScore(check(context,ssn));
    }

    @Async
    private Future<Integer> check(AsyncCheckRiskContext context, String ssn){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(blackList.contains(ssn)){
            context.logHistory("riskCheck: blacklisted");
            return new AsyncResult<>(100);
        }
        return new AsyncResult<>(0);

    }
}

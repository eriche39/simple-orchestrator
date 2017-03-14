/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.sample.context;

import com.github.ehe.simpleorchestrator.context.HistoryContext;

import java.util.concurrent.Future;

/**
 * Created by eric on 3/12/17.
 */
public interface AsyncCheckRiskContext extends HistoryContext<String> {
    String getSsn();
    void setRiskScore(Future<Integer> score);
}

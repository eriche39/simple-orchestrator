/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.context;

/**
 * a context that allow logging history
 */
public interface HistoryContext<H> extends Context {
    public void logHistory(H history);
}

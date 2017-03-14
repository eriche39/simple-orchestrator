/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.exception;

/**
 * a OrchestratorExceptionHandler can be attached on a channel or orchestrator
 * the handler can stop the channel/orchestrator by rethrown an exception
 */
public interface OrchestratorExceptionHandler {
    public void handle(OrchestratorException e);
}

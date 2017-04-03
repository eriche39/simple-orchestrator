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

package com.github.ehe.simpleorchestrator.impl;

import com.github.ehe.simpleorchestrator.exception.OrchestratorException;
import com.github.ehe.simpleorchestrator.exception.OrchestratorExceptionHandler;

/**
 * default exception handler which stop the channel or orchestrator that it attached to.
 */
public class DefaultExceptionHandler implements OrchestratorExceptionHandler {
    @Override
    public void handle(OrchestratorException e) {
        throw e;
    }
}

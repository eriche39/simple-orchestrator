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

package com.github.ehe.simpleorchestrator;

import com.github.ehe.simpleorchestrator.context.Context;
import com.github.ehe.simpleorchestrator.exception.OrchestratorException;

/**
 * This <code>Task</code> interface represents one of many Steps in a
 * <code>Orchestrator</code> execution.
 * <p>
 * a Task execute upon it's context,
 *
 * @param <P> : the context type which pass through all tasks in a Orchestrator
 * @see Orchestrator
 * @see Context
 */
public interface Task<P extends Context> {
    /**
     * @param context <code>context</code> object that this Task is working on
     * @throws OrchestratorException
     */
    void execute(P context) throws OrchestratorException;
}

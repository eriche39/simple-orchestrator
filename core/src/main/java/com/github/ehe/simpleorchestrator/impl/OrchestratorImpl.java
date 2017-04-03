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

import com.github.ehe.simpleorchestrator.Orchestrator;
import com.github.ehe.simpleorchestrator.Task;
import com.github.ehe.simpleorchestrator.context.Context;
import com.github.ehe.simpleorchestrator.exception.OrchestratorExceptionHandler;

/**
 * This <code>OrchestratorImpl</code> class is the default <code>Orchestrator</code>
 * implementation.
 * <p>
 * for details, see {@link Orchestrator}
 *
 * @param <P> the context that compatible with all it's tasks.
 * @see Orchestrator
 */
public class OrchestratorImpl<P extends Context> extends AbstractListTask<P> implements Orchestrator<P> {
    /**
     * @param ts the list of tasks to be executed sequentially
     */
    public OrchestratorImpl(Task<? super P>... ts) {
        super(ts);
    }

    /**
     * @param ts               the list of tasks to be executed sequentially
     * @param exceptionHandler the orchestrator exception handler, can rethrow exception or just log error
     */
    public OrchestratorImpl(OrchestratorExceptionHandler exceptionHandler, Task<? super P>... ts) {
        super(exceptionHandler, ts);
    }

}

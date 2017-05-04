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
import com.github.ehe.simpleorchestrator.exception.OrchestratorException;
import com.github.ehe.simpleorchestrator.exception.OrchestratorExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This <code>AbstractListTask</code> class is the base for orchestratorImpl and Channel
 * <p>
 * for details, see {@link Orchestrator}
 *
 * @param <P> the context that compatible with all it's tasks.
 * @see Orchestrator
 */
public abstract class AbstractListTask<P extends Context> {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractListTask.class);

    private List<Task<? super P>> tasks;
    private OrchestratorExceptionHandler exceptionHandler = new DefaultExceptionHandler();

    /**
     * @param ts the list of tasks to be executed sequentially
     */
    public AbstractListTask(Task<? super P>... ts) {
        List<Task<? super P>> list = new ArrayList();
        Collections.addAll(list, ts);
        tasks = list;
    }

    /**
     * @param ts               the list of tasks to be executed sequentially
     * @param exceptionHandler the orchestrator exception handler, can rethrow exception or just log error
     */
    public AbstractListTask(OrchestratorExceptionHandler exceptionHandler, Task<? super P>... ts) {
        this(ts);
        this.exceptionHandler = exceptionHandler;
    }

    public void execute(final P context) throws OrchestratorException {
        if (logger.isDebugEnabled()) {
            logger.debug("orchestrator " + this.getClass().getName() + " started");
        }

        for (Task<? super P> task : tasks) {
            try {
                task.execute(context);
            } catch (OrchestratorException e) {
                logger.error(e.getMessage());
                exceptionHandler.handle(e);
            }
        }
    }

}

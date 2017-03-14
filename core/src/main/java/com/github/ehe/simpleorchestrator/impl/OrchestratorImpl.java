/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.impl;

import java.util.ArrayList;
import java.util.List;

import com.github.ehe.orchestrator.*;
import com.github.ehe.simpleorchestrator.Task;
import com.github.ehe.simpleorchestrator.context.Context;
import com.github.ehe.simpleorchestrator.exception.OrchestratorException;
import com.github.ehe.simpleorchestrator.exception.OrchestratorExceptionHandler;
import com.github.ehe.simpleorchestrator.Orchestrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This <code>OrchestratorImpl</code> class is the default <code>Orchestrator</code>
 * implementation.
 *
 * for details, see {@link Orchestrator}
 * 
 * @see Orchestrator
 * @param <P> the context that compatible with all it's tasks.
 * 
 */
public class OrchestratorImpl<P extends Context> implements Orchestrator<P> {

	protected static final Logger logger = LoggerFactory.getLogger(OrchestratorImpl.class);

	private List<Task<? super P>> tasks;
	private OrchestratorExceptionHandler exceptionHandler = new DefaultExceptionHandler();

	/**
	 *
	 * @param ts the list of tasks to be executed sequencely
	 */
	public OrchestratorImpl(Task<? super P>... ts) {
		List<Task<? super P>> list = new ArrayList<>();
		for(Task<? super P> t : ts){
			list.add(t);
		}
		tasks = list;
	}

	/**
	 *
	 * @param ts the list of tasks to be executed sequencely
	 * @param exceptionHandler the orchestrator exception handler, can rethrew exception or just log error
	 */
	public OrchestratorImpl(OrchestratorExceptionHandler exceptionHandler, Task<? super P>... ts ) {
		this(ts);
		this.exceptionHandler = exceptionHandler;
	}

	@Override
	public void execute(final P context) throws OrchestratorException {

		if (logger.isDebugEnabled()) {
			logger.debug("orchestrator " + this.getClass().getName() + " started");
		}

		for (Task<? super P> task : tasks) {
			try {
				task.execute(context);
			} catch (OrchestratorException e){
				logger.error(e.getMessage());
				exceptionHandler.handle(e);
			}
		}
	}
}

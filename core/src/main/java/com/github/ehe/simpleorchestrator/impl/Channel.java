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

import com.github.ehe.simpleorchestrator.Task;
import com.github.ehe.simpleorchestrator.context.Context;
import com.github.ehe.simpleorchestrator.exception.OrchestratorExceptionHandler;

/**
 * a <code>Channel</code> class is a sequence list of tasks act as a single task
 * <p>
 * it groups a list of tasks to form a broader reusable functional task
 * <p>
 * another usage is to provide a way for selector to choose a list of tasks
 * <p>
 * it should to be used directly without subclass
 */

public class Channel<P extends Context> extends AbstractListTask<P> implements Task<P> {

    public Channel(Task<? super P>... tasks) {
        super(tasks);
    }

    public Channel(OrchestratorExceptionHandler exceptionHandler, Task<? super P> ts) {
        super(exceptionHandler, ts);
    }

}

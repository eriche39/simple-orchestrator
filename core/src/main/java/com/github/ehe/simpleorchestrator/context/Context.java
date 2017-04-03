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

package com.github.ehe.simpleorchestrator.context;

import com.github.ehe.simpleorchestrator.Orchestrator;
import com.github.ehe.simpleorchestrator.Task;

/**
 * The <code>context</code> interface defines data points that a Task or Orchestrator
 * can act upon.
 * <p>
 * when used for a Task, it defines the task's input and output.
 * when used for an Orchestrator, it defines inputs and outputs of all tasks
 * that the orchestrator needed to perform orchestration.
 *
 * @see Task
 * @see Orchestrator
 */
public interface Context {

}

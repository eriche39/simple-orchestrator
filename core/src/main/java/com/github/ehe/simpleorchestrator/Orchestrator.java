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
import com.github.ehe.simpleorchestrator.impl.OrchestratorImpl;

/**
 * Concept:
 * <ul>
 * <li>a Orchestrator interface represents a process across multiple Tasks.
 * <li>a Orchestrator is associated with a context and contains a list of Tasks
 * <li>a Task is a functional block of code that may take some input and contribute result
 * it's input and out is defined by it's associated Context interface
 * <li>a Context is an interface that defines input/output of an Task or a Orchestrator
 * <li>a Channel is a list of tasks that provide a broader functionality and act as single task
 * <li>a Selector is a map of enum to task(or channel), at runtime, only one enum can be the evaluated
 * result, and it's associated task(or channel) will be executed, others will be skipped.
 * <li>a default Orchestrator implementation is provided as {@link OrchestratorImpl}
 * </ul>
 * <p>
 * Usage:
 * <blockquote><pre>
 * step1:
 * Design tasks for your application that are reusable function blocks:
 *      1.1 Create a task context interface for each task with the inputs/outputs of the task.
 *      1.2 Create Task classes and implement execute(theContext) method.
 *
 * step2: (optional)
 * Design your channel that contains group of tasks.
 *
 * Step3: (optional)
 * Design your selector when you have cases that base on condition, some task need to be skipped.
 *
 * step4:
 * For each process, decide what Tasks it needed to run in sequence.
 *      4.1 Create orchestrator context that implements all it's task's contexts.
 *          (this is only place that you actually implement a context)
 *          a context implementation is mostly like implement an entity class, with getters and setters.
 *          a context usually have a initialization method that put initial data that some earlier tasks
 *              needed, later task will naturally use earlier task's output as input.
 *          a context usually have methods for orchestration client to get the result of orchestration.
 *      4.2 Create orchestrator instance by using OrchestratorImpl class and add task instances.
 * </pre></blockquote>
 *
 * @param <P> : orchestrator context type
 * @see Context
 * @see Task
 * @see OrchestratorImpl
 */
public interface Orchestrator<P extends Context> {

    /**
     * Execute the <code>Task</code>s, by the order of their position in
     * task list,
     * (Note: the tasks are executed in the same thread as the caller)
     *
     * @param context the <code>context</code> object
     * @throws OrchestratorException if there is an issue
     */
    void execute(P context) throws OrchestratorException;

}

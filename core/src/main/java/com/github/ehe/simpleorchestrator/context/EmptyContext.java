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

/**
 * a context that has no input/output
 * <p>
 * can be used for Task that don't need data for execution
 * and do not contribute data to other task or orchestrator
 * ie. a task that only delays for a fixed amount of time.
 * <p>
 * create this for readability.
 */
public interface EmptyContext<H> extends Context {
}

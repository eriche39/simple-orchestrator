/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.context;

/**
 * a context class that allow logging error
 *
 */
public interface ErrorContext<E> extends Context {
    public void logError(E error);
}

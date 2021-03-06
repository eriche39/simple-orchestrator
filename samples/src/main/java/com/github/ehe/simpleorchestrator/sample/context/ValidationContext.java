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

package com.github.ehe.simpleorchestrator.sample.context;

import com.github.ehe.simpleorchestrator.context.HistoryContext;

public interface ValidationContext<A> extends HistoryContext<String> {
    A getApplication();
}

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

package com.github.ehe.simpleorchestrator.meta;

import java.util.List;

/**
 * Meta data for Task
 */
public class TaskMeta extends Meta {

    public TaskMeta(String beanName, String className, String context, List<String> parents) {
        super(Type.TASK, beanName, className, context, parents);
    }
}

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

import java.util.ArrayList;
import java.util.List;

/**
 * Meta data for Orchestrator
 */
public class OrchestratorMeta extends Meta {
    private List<Meta> taskList = new ArrayList<>();

    public OrchestratorMeta(String beanName, String className, String context, List<String> parents) {
        super(Type.ORCHESTRATOR, beanName, className, context, parents);
    }

    public List<Meta> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Meta> taskList) {
        this.taskList = taskList;
    }
}

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
 * Meta data for Channel
 */
public class ChannelMeta extends Meta {
    private List<Meta> taskList = new ArrayList<>();

    public ChannelMeta(String beanName, String className) {
        super(Type.CHANNEL, beanName, className);
    }

    public List<Meta> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Meta> taskList) {
        this.taskList = taskList;
    }
}

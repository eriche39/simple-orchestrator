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
 * Meta data for Orchestrator's components
 */
public class Meta {
    private Type type;
    private String beanName;
    private String className;
    private String context;
    private List<String> parents = new ArrayList<>();

    public Meta(Type type, String beanName, String className, String context, List<String> parents) {
        this.type = type;
        this.beanName = beanName;
        this.className = className;
        this.context = context;
        this.parents = parents;
    }

    public Meta(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public String getBeanName() {
        return beanName;
    }

    public String getClassName() {
        return className;
    }

    public String getContext() {
        return context;
    }

    public List<String> getParents() {
        return parents;
    }

    public enum Type {
        TASK, SELECTOR, CHANNEL, ORCHESTRATOR
    }
}

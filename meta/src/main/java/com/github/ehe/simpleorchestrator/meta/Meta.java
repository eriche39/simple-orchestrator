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

/**
 * Meta data for Orchestrator's components
 */
public class Meta {
    private Type type;
    private String beanName;
    private String className;
    public Meta(Type type, String beanName, String className) {

        this.type = type;
        this.beanName = beanName;
        this.className = className;
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

    public enum Type {
        TASK, SELECTOR, CHANNEL, ORCHESTRATOR
    }
}

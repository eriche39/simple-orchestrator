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

import java.util.Map;

/**
 * Meta data for Selector
 */
public class SelectorMeta extends Meta {
    private Map<String, Meta> map;

    public SelectorMeta(String beanName, String className) {
        super(Type.SELECTOR, beanName, className);
    }

    public Map<String, Meta> getMap() {
        return map;
    }

    public void setMap(Map<String, Meta> map) {
        this.map = map;
    }

}

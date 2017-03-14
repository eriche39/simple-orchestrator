/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.sample.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * Created by eric on 3/12/17.
 */
@JsonPropertyOrder({"name", "approved", "history"})
public class AppliationResult {
    private String name;
    private boolean isApproved;
    private List<String> history;

    public AppliationResult(String name, boolean isApproved, List<String> history) {
        this.name = name;
        this.isApproved = isApproved;
        this.history = history;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }

}

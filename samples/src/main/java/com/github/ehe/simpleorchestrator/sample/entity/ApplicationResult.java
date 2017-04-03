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

package com.github.ehe.simpleorchestrator.sample.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.ehe.simpleorchestrator.sample.task.ValidationTask;

import java.util.List;

@JsonPropertyOrder({"name", "approved", "history", "errors"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationResult {
    private String name;
    private boolean isApproved;
    private List<String> history;

    private List<ValidationTask.ValidationError> errors;

    public ApplicationResult(String name, boolean isApproved, List<String> history) {
        this.name = name;
        this.isApproved = isApproved;
        this.history = history;
    }

    public ApplicationResult(List<ValidationTask.ValidationError> errors) {
        this.errors = errors;
    }

    public ApplicationResult() {
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

    public List<ValidationTask.ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationTask.ValidationError> errors) {
        this.errors = errors;
    }

}

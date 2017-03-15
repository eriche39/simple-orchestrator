/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.sample.exception;

import com.github.ehe.simpleorchestrator.exception.OrchestratorException;
import com.github.ehe.simpleorchestrator.sample.task.ValidationTask;

import java.util.List;

public class ValidationException extends OrchestratorException{

    private List<ValidationTask.ValidationError> errors;

    public ValidationException(List<ValidationTask.ValidationError> errors) {
        super("Validation exception", null);
        this.errors = errors;
    }

    public List<ValidationTask.ValidationError> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationTask.ValidationError> errors) {
        this.errors = errors;
    }
}

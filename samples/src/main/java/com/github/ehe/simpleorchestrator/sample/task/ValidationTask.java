/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.sample.task;

import com.github.ehe.simpleorchestrator.Task;
import com.github.ehe.simpleorchestrator.exception.OrchestratorException;
import com.github.ehe.simpleorchestrator.sample.context.ValidationContext;
import com.github.ehe.simpleorchestrator.sample.exception.ValidationException;
import org.glassfish.jersey.jaxb.internal.XmlCollectionJaxbProvider;
import org.glassfish.jersey.message.internal.StringBuilderUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidationTask<A> implements Task<ValidationContext<A>> {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    @Override
    public void execute(ValidationContext<A> context) throws OrchestratorException {
        A application = context.getApplication();

        Set<ConstraintViolation<A>> constraintViolations
                =validator.validate(application, Default.class);

        if(constraintViolations.size()>0){
            List<ValidationError> errors =
                    constraintViolations.stream().map(v -> new ValidationError(v.getMessage(), v.getPropertyPath().toString()))
                    .collect(Collectors.toList());
            throw new ValidationException(errors);
        }
    }

    public static class ValidationError{
        private String message;
        private String path;

        public ValidationError(String message, String path) {
            this.message = message;
            this.path = path;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}

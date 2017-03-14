/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.exception;

/**
 * The <code>OrchestratorException</code> is used for exception while executing a task
 * when a exception case happened, it can wrap catched exception with or just create a
 * OrchestratorException
 */
public class OrchestratorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private int errcode = 0;

	public OrchestratorException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public OrchestratorException(String msg, int errcode) {
		super(msg);
		this.errcode = errcode;
	}

	public OrchestratorException(String msg, int errcode, Throwable cause) {
		super(msg, cause);
		this.errcode = errcode;
	}

	public int getErrcode() {
		return errcode;
	}
}

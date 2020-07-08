package com.library.demo.core.exception;

import com.library.demo.core.page.Condition.Operator;

@SuppressWarnings("serial")
public final class InvlidOperatorException extends QueryException {

	public InvlidOperatorException(String property, String operatorStr) {
		this(property, operatorStr, null);
	}

	public InvlidOperatorException(String property, String operatorStr, Throwable cause) {
		super("Invalid   Operator property [" + property + "], " + "operator [" + operatorStr + "], must be one of "
				+ Operator.toStringAllOperator(), cause);
	}
}

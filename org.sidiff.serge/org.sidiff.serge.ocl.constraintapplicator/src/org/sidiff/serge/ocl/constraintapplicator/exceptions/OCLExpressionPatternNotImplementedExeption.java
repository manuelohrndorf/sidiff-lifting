package org.sidiff.serge.ocl.constraintapplicator.exceptions;

import org.eclipse.ocl.expressions.OCLExpression;


@SuppressWarnings("serial")
public class OCLExpressionPatternNotImplementedExeption extends Exception{


	public OCLExpressionPatternNotImplementedExeption(OCLExpression oclExpression) {
		super(oclExpression.getName() + "not yet supported By OCLConstraintApplicator");
	}
}

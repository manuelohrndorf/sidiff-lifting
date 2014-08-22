package org.sidiff.editrule.generator.ocl.constraintapplicator.exceptions;

import org.eclipse.ocl.expressions.OCLExpression;


@SuppressWarnings("serial")
public class OCLExpressionPatternNotImplementedExeption extends Exception{


	/**
	 * Exception should be used if an OCLExpression occurs, which is not implemented
	 * in one of our OCLExpressionPatterns.
	 * 
	 * @param oclExpression
	 */
	public OCLExpressionPatternNotImplementedExeption(OCLExpression oclExpression) {
		super("Null-value or the follwoing expression "+ oclExpression.getName() + " is not yet supported by OCLConstraintApplicator");
	}
}

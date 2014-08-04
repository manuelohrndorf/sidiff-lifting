package org.sidiff.serge.ocl.constraintapplicator.oclevaluators;

import org.eclipse.ocl.expressions.OCLExpression;

/**
 * Abstract Evaluator class which other evaluators extend.
 * This class defines several methods for checking which kind of
 * OClExpressionPatterns matches on given OCLExpressions.
 * @author mrindt, ...
 *
 */
public abstract class AbstractEvaluator {

	/**
	 * Finds out if the given OCLExpression is simple an EAttributeValueCondition
	 * without further encapsulated OCLExpressions.
	 * See also {@link AttributeValueCondition} 
	 * @param oclxp
	 * @return true or false
	 */
	public static boolean isFlatAttributeValueCondition(OCLExpression oclxp) {
		
		//..
		
		return false;
		
	}

	/**
	 * Finds out if the given OCLExpression is simple an EAttributeValueDependencyCondition
	 * without further encapsulated OCLExpressions.
	 * See also {@link AttributeValueDependencyCondition} 
	 * @param oclxp
	 * @return true or false
	 */
	public static boolean isFlatAttributeValueDependencyCondition(OCLExpression oclxp) {
		
		//..
		
		return false;
	}
	
	/**
	 * Finds out if the given OCLExpression is simple an EReferenceListCondition
	 * without further encapsulated OCLExpressions.
	 * See also {@link ReferenceListCondition} 
	 * @param oclxp
	 * @return true or false
	 */
	public static boolean isFlatReferenceListCondition(OCLExpression oclxp) {
		
		//..
		
		return false;
		
	}
	
	/**
	 * Finds out if the given OCLExpression is simple an EReferenceAssignmentCondition
	 * without further encapsulated OCLExpressions.
	 * See also {@link ReferenceAssignmentCondition} 
	 * @param oclxp
	 * @return true or false
	 */
	public static boolean isFlatReferenceAssignmentCondition(OCLExpression oclxp) {
		
		
		//..
		
		return false;
	}
	
}

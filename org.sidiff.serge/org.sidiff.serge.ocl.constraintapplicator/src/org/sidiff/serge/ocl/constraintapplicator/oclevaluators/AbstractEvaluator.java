package org.sidiff.serge.ocl.constraintapplicator.oclevaluators;

import org.eclipse.ocl.expressions.OCLExpression;
import org.sidiff.serge.ocl.constraintapplicator.oclpatterns.AttributeValueCondition;
import org.sidiff.serge.ocl.constraintapplicator.oclpatterns.AttributeValueDependencyCondition;
import org.sidiff.serge.ocl.constraintapplicator.oclpatterns.ReferenceAssignmentCondition;
import org.sidiff.serge.ocl.constraintapplicator.oclpatterns.ReferenceListCondition;

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
	 * @return ExpressionDetails {@link ExpressionDetails} if it matches
	 */
	public static ExpressionDetails matchFlatAttributeValueCondition(OCLExpression oclxp) {
		
		ExpressionDetails expDetails = null;
		
		//..
		
		return expDetails;
		
	}

	/**
	 * Finds out if the given OCLExpression is simple an EAttributeValueDependencyCondition
	 * without further encapsulated OCLExpressions.
	 * See also {@link AttributeValueDependencyCondition} 
	 * @param oclxp
	 * @return ExpressionDetails {@link ExpressionDetails} if it matches
	 */
	public static ExpressionDetails matchFlatAttributeValueDependencyCondition(OCLExpression oclxp) {
		
		ExpressionDetails expDetails = null;
		
		//..
		
		return expDetails;
	}
	
	/**
	 * Finds out if the given OCLExpression is simple an EReferenceListCondition
	 * without further encapsulated OCLExpressions.
	 * See also {@link ReferenceListCondition} 
	 * @param oclxp
	 * @return ExpressionDetails {@link ExpressionDetails} if it matches
	 */
	public static ExpressionDetails matchFlatReferenceListCondition(OCLExpression oclxp) {
		
		ExpressionDetails expDetails = null;
		
		//..
		
		return expDetails;
	}
	
	/**
	 * Finds out if the given OCLExpression is simple an EReferenceAssignmentCondition
	 * without further encapsulated OCLExpressions.
	 * See also {@link ReferenceAssignmentCondition} 
	 * @param oclxp
	 * @return ExpressionDetails {@link ExpressionDetails} if it matches
	 */
	public static ExpressionDetails matchFlatReferenceAssignmentCondition(OCLExpression oclxp) {
		
		ExpressionDetails expDetails = null;
		
		//..
		
		return expDetails;
	}

	
}

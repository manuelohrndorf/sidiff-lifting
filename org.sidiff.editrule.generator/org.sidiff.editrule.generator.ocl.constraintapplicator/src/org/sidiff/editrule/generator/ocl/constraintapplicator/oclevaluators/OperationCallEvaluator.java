package org.sidiff.editrule.generator.ocl.constraintapplicator.oclevaluators;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.ocl.ecore.OperationCallExp;
import org.eclipse.ocl.expressions.OCLExpression;
import org.sidiff.editrule.generator.ocl.constraintapplicator.exceptions.OCLExpressionPatternNotImplementedExeption;
import org.sidiff.editrule.generator.ocl.constraintapplicator.oclpatterns.AttributeValueCondition;
import org.sidiff.editrule.generator.ocl.constraintapplicator.oclpatterns.AttributeValueDependencyCondition;
import org.sidiff.editrule.generator.ocl.constraintapplicator.oclpatterns.OCLExpressionPattern;
import org.sidiff.editrule.generator.ocl.constraintapplicator.oclpatterns.ReferenceAssignmentCondition;
import org.sidiff.editrule.generator.ocl.constraintapplicator.oclpatterns.ReferenceListCondition;

/**
 * This evaluator class corresponds to OperationCall expressions
 * {@link OperationCallExp}.
 * 
 * @author mrindt
 *
 */
public class OperationCallEvaluator extends AbstractEvaluator{

	/**
	 * This method evaluates which type of condition the given
	 * OperationCallExp beholds and returns the corresponding
	 * OClExpressionPattern in case we support it.
	 * 
	 * @param oclxp
	 * @param containerClassifier
	 * @return
	 * @throws OCLExpressionPatternNotImplementedExeption
	 */
	public OCLExpressionPattern evaluate(OCLExpression<EClassifier> oclxp, EClassifier containerClassifier) throws OCLExpressionPatternNotImplementedExeption {
		
		OCLExpressionPattern oclxpp = null;
		
		ExpressionDetails expDetails = matchFlatAttributeValueCondition(oclxp);
		
		if(expDetails!=null) {
			oclxpp = new AttributeValueCondition(oclxp,expDetails);	
			return oclxpp;	
		}
		
		// if not returned yet, try to match other pattern
		expDetails = matchFlatAttributeValueDependencyCondition(oclxp);
		if(expDetails!=null) {
			oclxpp = new AttributeValueDependencyCondition(oclxp, expDetails);	
			return oclxpp;	
		}
		
		// if not returned yet, try to match other pattern		
		expDetails = matchFlatReferenceListCondition(oclxp);
		if(expDetails!=null){
			oclxpp = new ReferenceListCondition(oclxp, expDetails);
			return oclxpp;	
		}
		
		// if not returned yet, try to match other pattern
		expDetails = matchFlatReferenceAssignmentCondition(oclxp);
		if(expDetails!=null){
			oclxpp = new ReferenceAssignmentCondition(oclxp,expDetails);
			return oclxpp;	
		}
		
		// if still null, then we currently have no pattern to match
		if(expDetails==null) {
			throw new OCLExpressionPatternNotImplementedExeption(oclxp);
		}
		
		return oclxpp;

		
	}
	
	/**
	 * Fetches the details of an OCLExpression (e.g. involved EAttributes)
	 * @param oclxp
	 * @return ExpressionDetails
	 */
	private static ExpressionDetails getExpressionDetails(OCLExpression oclxp) {
		
		// ...
		
		
		return null;
		
	}
}

package org.sidiff.serge.ocl.constraintapplicator.oclevaluators;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.ocl.expressions.OCLExpression;
import org.sidiff.serge.ocl.constraintapplicator.exceptions.OCLExpressionPatternNotImplementedExeption;
import org.sidiff.serge.ocl.constraintapplicator.oclpatterns.AttributeValueCondition;
import org.sidiff.serge.ocl.constraintapplicator.oclpatterns.AttributeValueDependencyCondition;
import org.sidiff.serge.ocl.constraintapplicator.oclpatterns.OCLExpressionPattern;
import org.sidiff.serge.ocl.constraintapplicator.oclpatterns.ReferenceAssignmentCondition;
import org.sidiff.serge.ocl.constraintapplicator.oclpatterns.ReferenceListCondition;

public class OperationCallEvaluator extends AbstractEvaluator{

	public OCLExpressionPattern evaluate(OCLExpression oclxp, EClassifier containerClassifier) throws OCLExpressionPatternNotImplementedExeption {
		
		OCLExpressionPattern oclxpp = null;
		
		if(isFlatAttributeValueCondition(oclxp)) {
			oclxpp = new AttributeValueCondition(oclxp,containerClassifier);
		}
		
		else if(isFlatAttributeValueDependencyCondition(oclxp)) {
			oclxpp = new AttributeValueDependencyCondition(oclxp,containerClassifier);
		}
		
		else if(isFlatReferenceListCondition(oclxp)) {
			oclxpp = new ReferenceListCondition(oclxp,containerClassifier);
		}
		
		else if(isFlatReferenceAssignmentCondition(oclxp)) {
			oclxpp = new ReferenceAssignmentCondition(oclxp,containerClassifier);
		}
		
		else {
			throw new OCLExpressionPatternNotImplementedExeption(oclxp);
		}
		
		return oclxpp;	
	}
}

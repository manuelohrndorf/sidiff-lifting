package org.sidiff.serge.ocl.constraintapplicator.oclpatterns;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.ocl.expressions.OCLExpression;
import org.sidiff.serge.ocl.constraintapplicator.modulepatterns.ModuleContainmentPattern;
import org.sidiff.serge.ocl.constraintapplicator.modulepatterns.ModuleNamePattern;
import org.sidiff.serge.ocl.constraintapplicator.oclevaluators.ExpressionDetails;

/**
 * This class specifies the OCLExpressionPattern ReferenceListCondition.
 * It consists of the corresponding OCLExpression and an EClassifier,
 * which is the container of the OCLExpression. It also contains
 * a reference to the corresponding ModuleMatchPattern, to find
 * affected Modules.
 * <br/><br/>
 * An ReferenceListCondition can be e.g.:
 * <br/><br/>
 * <b>"vehicle.personen->size() >=1 "</b>
 * <br/><br/>
 * @author mrindt, ...
 *
 */
public class ReferenceListCondition extends OCLExpressionPattern {

	/**
	 * The operator.
	 */
	private String operator = null;
	
	/**
	 * The involved EReference.
	 */
	private EReference eReference = null;
	
	/**
	 * Constructor.
	 * @param oclxp
	 * @param expDetails
	 */
	public ReferenceListCondition(OCLExpression<EClassifier> oclxp, ExpressionDetails expDetails) {
		
		this.oclxp = oclxp;
		this.container = expDetails.getInvolvedContext();
		this.eReference = expDetails.getInvolvedEReference();
		this.operator = expDetails.getOperator();
		
		ModuleNamePattern mnp = new ModuleNamePattern();
		
		// ..
		
		ModuleContainmentPattern mcp = new ModuleContainmentPattern();
		
		// ..
		
		respectiveModuleMatchPatterns.add(mnp);
		respectiveModuleMatchPatterns.add(mcp);
	}
	
}

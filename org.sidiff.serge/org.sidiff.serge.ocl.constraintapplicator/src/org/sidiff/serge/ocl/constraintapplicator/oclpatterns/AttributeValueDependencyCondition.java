package org.sidiff.serge.ocl.constraintapplicator.oclpatterns;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.ocl.expressions.OCLExpression;
import org.sidiff.serge.ocl.constraintapplicator.modulepatterns.ModuleContainmentPattern;
import org.sidiff.serge.ocl.constraintapplicator.modulepatterns.ModuleNamePattern;
import org.sidiff.serge.ocl.constraintapplicator.oclevaluators.ExpressionDetails;


/**
 * This class specifies the OCLExpressionPattern AttributeValueDependencyCondition.
 * It consists of the corresponding OCLExpression and an EClassifier,
 * which is the container of the OCLExpression. It also contains
 * a reference to the corresponding ModuleMatchPattern, to find
 * affected Modules.
 * <br/><br/>
 * An AttributeValueDependencyCondition can be e.g.:
 * <br/><br/>
 * <b>"vehicle.type= 'car' implies vehicle.numberOfWheels = 4"</b>
 * <br/><br/>
 * @author mrindt, ...
 *
 */
public class AttributeValueDependencyCondition extends OCLExpressionPattern{

	/**
	 * The operator in the expression.
	 */
	private String operator = null;
	
	/**
	 * The involved EAttribute.
	 */
	private EAttribute eAttribute = null;
	
	/**
	 * Constructor.
	 * @param oclxp
	 * @param containerClassifier
	 */
	public AttributeValueDependencyCondition(OCLExpression<EClassifier> oclxp, ExpressionDetails expDetails) {
	
		this.oclxp = oclxp;
		this.container = expDetails.getInvolvedContext();
		this.eAttribute = expDetails.getInvolvedEAttribute();
		this.operator = expDetails.getOperator();
		
		ModuleNamePattern mnp = new ModuleNamePattern();
		
		// ..
		
		ModuleContainmentPattern mcp = new ModuleContainmentPattern();
		
		// ..
		
		respectiveModuleMatchPatterns.add(mnp);
		respectiveModuleMatchPatterns.add(mcp);
		
	}
	
}

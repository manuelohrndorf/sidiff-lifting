package org.sidiff.editrule.generator.ocl.constraintapplicator.oclpatterns;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.ocl.expressions.OCLExpression;
import org.sidiff.editrule.generator.ocl.constraintapplicator.modulepatterns.ModuleContainmentPattern;
import org.sidiff.editrule.generator.ocl.constraintapplicator.modulepatterns.ModuleNamePattern;
import org.sidiff.editrule.generator.ocl.constraintapplicator.oclevaluators.ExpressionDetails;

/**
 * This class specifies the OCLExpressionPattern AttributeValueCondition.
 * It consists of the corresponding OCLExpression and an EClassifier,
 * which is the container of the OCLExpression. It also contains
 * a reference to the corresponding ModuleMatchPattern, to find
 * affected Modules.
 * <br/><br/>
 * An AttributeValueCondition can be e.g.:
 * <br/><br/>
 * <b>"Person.age >= 18"</b>
 * <br/><br/>
 * @author mrindt, ...
 *
 */
public class AttributeValueCondition extends OCLExpressionPattern{
	
	/**
	 * The operator.
	 */
	private String operator = null;
	
	/**
	 * The involved EAttribute.
	 */
	private EAttribute eAttribute = null;
	
	
	/**
	 * Constructor.
	 * @param oclxp
	 * @param expDetails
	 */
	public AttributeValueCondition(OCLExpression<EClassifier> oclxp, ExpressionDetails expDetails) {
		
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

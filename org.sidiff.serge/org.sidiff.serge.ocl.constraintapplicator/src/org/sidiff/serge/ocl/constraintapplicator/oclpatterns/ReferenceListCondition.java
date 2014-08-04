package org.sidiff.serge.ocl.constraintapplicator.oclpatterns;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.ocl.expressions.OCLExpression;
import org.sidiff.serge.ocl.constraintapplicator.modulepatterns.ModuleContainmentPattern;
import org.sidiff.serge.ocl.constraintapplicator.modulepatterns.ModuleNamePattern;

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
	 * Constructor
	 * @param oclxp
	 * @param containerClassifier
	 */
	public ReferenceListCondition(OCLExpression<EClassifier> oclxp, EClassifier containerClassifier) {
		
		this.oclxp = oclxp;
		this.container = containerClassifier;
		
		ModuleNamePattern mnp = new ModuleNamePattern();
		
		// ..
		
		ModuleContainmentPattern mcp = new ModuleContainmentPattern();
		
		// ..
		
		respectiveModuleMatchPatterns.add(mnp);
		respectiveModuleMatchPatterns.add(mcp);
	}
	
}

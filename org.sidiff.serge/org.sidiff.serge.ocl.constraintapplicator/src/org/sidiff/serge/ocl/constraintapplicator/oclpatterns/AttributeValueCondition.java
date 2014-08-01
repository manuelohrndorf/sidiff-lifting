package org.sidiff.serge.ocl.constraintapplicator.oclpatterns;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.ocl.expressions.OCLExpression;
import org.sidiff.serge.ocl.constraintapplicator.modulepatterns.ModuleContainmentPattern;
import org.sidiff.serge.ocl.constraintapplicator.modulepatterns.ModuleNamePattern;

public class AttributeValueCondition extends OCLExpressionPattern{
	
	public AttributeValueCondition(OCLExpression<EClassifier> oclxp, EClassifier containerClassifier) {
		
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

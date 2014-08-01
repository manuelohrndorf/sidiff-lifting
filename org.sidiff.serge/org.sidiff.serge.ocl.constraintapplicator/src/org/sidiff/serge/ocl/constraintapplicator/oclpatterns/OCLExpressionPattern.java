package org.sidiff.serge.ocl.constraintapplicator.oclpatterns;

import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.ocl.expressions.OCLExpression;
import org.sidiff.serge.ocl.constraintapplicator.modulepatterns.ModuleMatchPattern;

public abstract class OCLExpressionPattern {

	protected OCLExpression<EClassifier> oclxp;
	protected EModelElement container;
	protected Boolean isEncapsulated;
	
	protected Set<ModuleMatchPattern> respectiveModuleMatchPatterns;
	
}

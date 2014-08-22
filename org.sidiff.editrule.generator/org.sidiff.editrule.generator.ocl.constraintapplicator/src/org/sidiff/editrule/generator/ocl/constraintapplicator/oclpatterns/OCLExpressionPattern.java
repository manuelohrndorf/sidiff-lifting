package org.sidiff.editrule.generator.ocl.constraintapplicator.oclpatterns;

import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.ocl.expressions.OCLExpression;
import org.sidiff.editrule.generator.ocl.constraintapplicator.modulepatterns.ModuleMatchPattern;

/**
 * Abstract class for an OCLExpressionPattern.
 * Its sub-classes define our supported OCL patterns
 * which will allow the modification of an existing
 * module set of edit rules.
 * 
 * @author mrindt
 *
 */
public abstract class OCLExpressionPattern {

	protected OCLExpression<EClassifier> oclxp;
	protected EModelElement container;
	protected Boolean isEncapsulated;
	
	protected Set<ModuleMatchPattern> respectiveModuleMatchPatterns;
	
	
	public OCLExpression<EClassifier> getOCLExpression() {
		return oclxp;
	}


	public EModelElement getContainer() {
		return container;
	}


	public Boolean getIsEncapsulated() {
		return isEncapsulated;
	}
}

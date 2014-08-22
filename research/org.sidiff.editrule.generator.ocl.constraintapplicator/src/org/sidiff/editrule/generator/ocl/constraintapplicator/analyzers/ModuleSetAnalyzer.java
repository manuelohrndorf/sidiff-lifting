package org.sidiff.editrule.generator.ocl.constraintapplicator.analyzers;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.editrule.generator.ocl.constraintapplicator.modulepatterns.ModuleMatchPattern;
import org.sidiff.editrule.generator.ocl.constraintapplicator.oclpatterns.OCLExpressionPattern;

/**
 * This Class is responsible to find out if modules are affected by given
 * OCLExpressionPatterns.
 * 
 * @author mrindt, ..
 *
 */
public class ModuleSetAnalyzer {

	/**
	 * This method finds out obvious module affections (e.g. if certain
	 * EAttribute/EReference/EClassifier types occure in module file names)
	 * @param oclxpps
	 * @return set of affected modules.
	 */
	private Set<Module> findDirectlyAffectedModules(OCLExpressionPattern oclxpps) {
		
		//..
		//consider ModuleNamePattern-class.
		
		return null;
	}
	
	/**
	 * This method finds out the obscured module affections (e.g. if certain
	 * EAttribute/EReference/EClassifier types do not occur in module file names
	 * but as dangling elements inside a module instead (e.g. as a mandatory child).
	 * 
	 * @param oclxpps
	 * @return set of affected modules.
	 */
	private Set<Module> findIndirectlyaffectedModules(OCLExpressionPattern oclxpps) {

		//..
		// for this we can either consider
		// - ModuleContainmentPattern-class
		// - ModuleHOTPatterns-class (using HOT henshin modules)
		// - both.
		// can be implemented in the future, no high priority.
		
		
		return null;
	}
	
	/**
	 * This method will find out which modules are affected by a given OClExpression.
	 * @param oclxpps
	 * @return set of affected modules.
	 */
	public Set<Module> findAffectedModules(OCLExpressionPattern oclxpps) {
		
		Set<Module> completeSet = new HashSet<Module>();
		
		completeSet.addAll(completeSet);
			
		return completeSet;
		
	}
}

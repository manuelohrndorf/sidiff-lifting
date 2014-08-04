package org.sidiff.serge.ocl.constraintapplicator.store;

import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.helper.OCLHelper;
import org.sidiff.serge.ocl.constraintapplicator.modulepatterns.ModuleMatchPattern;
import org.sidiff.serge.ocl.constraintapplicator.oclpatterns.OCLExpressionPattern;
import org.sidiff.serge.ocl.constraintapplicator.settings.OCLCASettings;

/**
 * This class will hold all identified OCLExpressionPatterns
 * and all Henshin modules. Modules are also mapped to those OCLExpressionPatterns
 * which have an effect them.
 * 
 * @author mrindt
 *
 */
public class Constraint2ModuleStore {
	
	/**
	 * Set of all henshin modules.
	 */
	private HenshinResourceSet moduleSet;
	
	/**
	 * Set of all found and supported OCLExpressionPatterns.
	 */
	private Set<OCLExpressionPattern> oclXPPatternSet;
	
	/**
	 * Set of pairs of type (OCLExpressionPattern,Module).
	 */
	private Set<ConstraintModulePair> constraintModulePair;
	
	
	/**
	 * Constructor
	 * @param settings
	 */
	public Constraint2ModuleStore (OCLCASettings settings){
		
		// load modules
		String inputPath = settings.getInputPath();		
		moduleSet = new HenshinResourceSet(inputPath);
		
	}
	
	/**
	 * Adds a given OCLExpressionPattern to the internal set.
	 * @param oclxpp
	 */
	public void addOCLExpressionPattern(OCLExpressionPattern oclxpp) {
		oclXPPatternSet.add(oclxpp);
	}

	/**
	 * Returns a set of OCLExpressionPatterns kept in this class instance.
	 * @return
	 */
	public Set<OCLExpressionPattern> getAllConstraints() {
		return oclXPPatternSet;
	}

	/**
	 * Connects and OCLExpressionPattern to a set of thereby affected modules.
	 * @param oclxpp
	 * @param affectedModules
	 */
	public void addAffectedModules(OCLExpressionPattern oclxpp, Set<Module> affectedModules) {
		for(Module m: affectedModules) {
			ConstraintModulePair cmPair = new ConstraintModulePair(m, oclxpp);
			constraintModulePair.add(cmPair);
		}
	}
	
	/**
	 * This method finds and links all ConstraintModulePairs, where the same modules
	 * are affected by different OCLExpressionPatterns.
	 */
	public void linkMultipleModuleAffections() {
		//TODO for each ConstraintModulePair add additonalModuleAffections
		//...
	}


}

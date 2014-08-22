package org.sidiff.serge.ocl.constraintapplicator.store;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
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
	private Set<OCLExpressionPattern> oclXPPatternSet = new HashSet<OCLExpressionPattern>();
	
	/**
	 * Set of pairs of type (OCLExpressionPattern,Module).
	 */
	private Set<ConstraintModulePair> constraintModulePairs = new HashSet<ConstraintModulePair>();
	
	
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
			constraintModulePairs.add(cmPair);
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

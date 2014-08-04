package org.sidiff.serge.ocl.constraintapplicator.store;

import java.util.Set;

/**
 * This class is responsible for correctly
 * modifying the existing module set by means of
 * ConstraintModulePairs.
 * 
 * @author mrindt, ...
 *
 */
public class ModuleSetModifier {

	
	/**
	 * Replaces a module in the module set.
	 * @param pair
	 */
	private void replaceModule(ConstraintModulePair pair) {
		//..
	}
	
	/**
	 * Modifies a module in the module set.
	 * @param pair
	 */
	private void modifyModule(ConstraintModulePair pair) {
		//..
	}
	
	/**
	 * Removes a module in the module set.
	 * @param pair
	 */
	private void removeModule(ConstraintModulePair pair) {
		//..
	}
	
	/**
	 * Creates a new module in the module set.
	 * @param pair
	 */
	private void createAdditionalModule(ConstraintModulePair pair) {
		//..
	}
	
	/**
	 * This method will handle all ConstraintModulePairs, which do not
	 * have an internal reference to another ConstraintModulePair;
	 * i.e. it should ignore ConstraintModulePairs where more than one
	 * module is affected by the same OCLExpressionPattern.
	 * @param set
	 */
	private void handleSingleAffectedModules(Set<ConstraintModulePair> set) {
		//..
	}
	
	/**
	 * This method will handle all ConstraintModulePairs, whose
	 * OCLExpressionPattern does affect multiple Modules.
	 * @param set
	 */
	private void handleMultipleAffectedModules(Set<ConstraintModulePair> set) {
		//..
	}
	
	/**
	 * This method modifies the module set according to the mapped
	 * OCLExpressionPatterns. 
	 * @param c2mstore
	 */
	public void modify(Constraint2ModuleStore c2mstore) {
		//TODO delegate to class method see above
	}
	
}

package org.sidiff.serge.core;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;

public class SubTypeReplacer {

	/**
	 * The original module that needs to be examined for replacables.
	 */
	private Module origModule = null;
	
	/**
	 * The set containing the generated variants for the original module
	 */
	private Set<Module> variantModules = new HashSet<Module>();
	
	public SubTypeReplacer (Module originalModule){
		this.origModule = origModule;
	}
	
	public Set<Module> generateModulesBySuperTypeReplacesOfDanglingNodes() {
		
		//TODO
		
		return variantModules;			
	}
}

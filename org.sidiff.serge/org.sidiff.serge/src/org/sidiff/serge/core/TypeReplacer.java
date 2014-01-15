package org.sidiff.serge.core;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;

public class TypeReplacer {
	
	/**
	 * The original module that needs to be examined for replacables.
	 */
	private Module originalModule = null;
	
	/**
	 * The EClassifierInfoManagement.
	 */
	private EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();	
	
	/**
	 * Matrix containing the possible node type replacements
	 */
	private CombinationMatrix matrix = null;
	
	/**
	 * Constructor
	 * @param originalModule
	 */
	public TypeReplacer(Module originalModule) {
		this.originalModule = originalModule;
		
		matrix = new CombinationMatrix();
		matrix.calculateFor(originalModule);

	}
	

	

	public Set<Module> replace() {
		
		Set<Module> modules = new HashSet<Module>();		

		
		
		
		
		
		
		return modules;
		
	}
	

	
}

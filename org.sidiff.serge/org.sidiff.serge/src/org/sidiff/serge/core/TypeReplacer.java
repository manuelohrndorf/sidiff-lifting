package org.sidiff.serge.core;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.henshin.model.Module;

public class TypeReplacer {

	private static Module originalModule;
	
	/**
	 * Constructor
	 */
	public TypeReplacer(Module originalModule) {
		this.originalModule = originalModule;		

	}
	
	public Set<Module> replace() {
	
		Set<Module> modules = new HashSet<Module>();
	
		ReplacementTable rT = new ReplacementTable(originalModule);
		
		if(rT.hasReplacements()) {
			
			//...
			rT.printTable();
			
			
		}
		
		return modules;
	}
}

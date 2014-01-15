package org.sidiff.serge.core;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
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

		
		//TODO check for dirty bit --> new matrix
		
		for(MatrixRow row: matrix.getRows()) {
			
			for(EClassifier replacement: row.getOnlyReplacementClassifierEntries()) {
				Node originalNode = matrix.getOriginalNode(replacement, row);
				
				//TODO isAlllowedAsDangling for replacement...
				//TODO make sure, that first child is base of module is not replaced...
				// incase of non abstract
							
				
				// create copy
				Module copy = EcoreUtil.copy(originalModule);
				
				//TODO map all nodes between original and copy!!!!! och nö
				
				//TODO replace, variant naming by regex
				//TODO check mandatories
				//TODO check mandatories need replaces....
				
			}
			
		}
		
		
		
		
		return modules;
		
	}
	

	
}

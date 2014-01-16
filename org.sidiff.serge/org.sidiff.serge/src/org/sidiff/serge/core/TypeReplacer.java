package org.sidiff.serge.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
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
			
			// for each row create a copy
			Module copy = EcoreUtil.copy(originalModule);
			
			// iterate over all replacement entries in row
			for(EClassifier replacement: row.getOnlyReplacementClassifierEntries()) {
				Node originalNode = matrix.getOriginalNode(replacement, row);
				
				//TODO isAlllowedAsDangling for replacement...
				//TODO make sure, that first child is base of module is not replaced...
				// incase of non abstract

				// find and replace type of corresponding node in copy
				Iterator<EObject> iterOrig = originalModule.eAllContents();
				Iterator<EObject> iterCopy = copy.eAllContents();
				while(iterOrig.hasNext()) {
					EObject origElem = iterOrig.next();
					EObject copyElem = iterCopy.next();
					
					if(origElem.equals(originalNode)) {
						((Node)copyElem).setType((EClass)replacement);
					}
				}
				
				//Set a new name for this variant module (e.g. blabla_Variant1234)
				long id = System.nanoTime();				
				if(copy.getName().matches(".*(_Variant\\d*\\w*)$")) {
					copy.setDescription(copy.getDescription().replaceAll("(Variant\\d*)$", "Variant"+String.valueOf(id)));
					copy.setName(copy.getName().replaceAll("(Variant\\d*)$", "Variant"+String.valueOf(id)));
				}else{
					copy.setDescription(copy.getDescription()+" Variant"+id);
					copy.setName(copy.getName()	+"_Variant"+id);			
				}			
				
				//TODO check mandatories
				//TODO check mandatories need replaces....
				
				modules.add(copy);
				
			}
			
		}
		
		
		
		
		return modules;
		
	}
	

	
}

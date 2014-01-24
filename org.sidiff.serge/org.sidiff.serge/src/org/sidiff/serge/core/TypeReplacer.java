package org.sidiff.serge.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.serge.configuration.Configuration.OperationType;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;
import org.sidiff.serge.util.MatrixRow;

public class TypeReplacer {
	
	/**
	 * The original module that needs to be examined for replacables.
	 */
	private Module originalModule = null;
	
	/**
	 * The operaiton type of the originalModule
	 */
	private OperationType opType = null;
	
	/**
	 * If super type reduction for the given operation type is preferred for dangling mandatories
	 */
	private Boolean reduceToSuperType = null;
	
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
	 * @param opType
	 * @throws OperationTypeNotImplementedException 
	 */
	public TypeReplacer(Module originalModule, OperationType opType, boolean reduceToSuperType) throws OperationTypeNotImplementedException {
		
		this.originalModule 	= originalModule;
		this.opType 			= opType;
		this.reduceToSuperType  = reduceToSuperType;
		
		matrix = new CombinationMatrix(opType, reduceToSuperType);
		matrix.calculateFor(originalModule);

	}
	

	/**
	 * The replacement method, that replaces every possible node type
	 * that requires a replace to represent another valid variant of a module.
	 * @return
	 * 		The complete set of variants that can result from valid replacements.
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> replace() throws OperationTypeNotImplementedException {
		
		Set<Module> modules = new HashSet<Module>();
		
		if(matrix.columnIsDirty()) {
			
			//TODO
		}
		
		//TODO check for dirty bit --> new matrix
		//TODO dirty bit might not be necessary anymore.. at least not for rows. ..check tghat
		
		for(MatrixRow row: matrix.getRows()) {
			
			// for each row create a copy
			Module copy = EcoreUtil.copy(originalModule);
			
			// iterate over all replacement entries in row
			for(EClassifier replacement: row.getOnlyReplacementClassifierEntries()) {
				Node originalNode = matrix.getOriginalNode(replacement, row);
				
				//TODO make sure, that first child is base of module is not replaced...
				// incase of non abstract

				// find and replace type of corresponding node in copy
				Iterator<EObject> iterOrig = originalModule.eAllContents();
				Iterator<EObject> iterCopy = copy.eAllContents();
				while(iterOrig.hasNext()) {
					EObject origElem = iterOrig.next();
					EObject copyElem = iterCopy.next();
					
					if(origElem.equals(originalNode)) {
						Node copiedNode = ((Node)copyElem);
						copiedNode.setType((EClass)replacement);
						
						// create mandatories that might me necessary after replacements
						Rule rule = HenshinRuleAnalysisUtilEx.getRules(copy).get(0);
						EClassifierInfo replacementInfo = ECM.getEClassifierInfo(replacement);
						Common.createMandatoryChildren(rule, replacementInfo, copiedNode, opType, reduceToSuperType);
						Common.createMandatoryNeighbours(rule, replacementInfo, copiedNode, opType, reduceToSuperType);
						
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
				

				// add module to result list
				modules.add(copy);
				
				//TODO let original module be added to modules if it is also consistent
			}		
		}
		return modules;
	}
	
}

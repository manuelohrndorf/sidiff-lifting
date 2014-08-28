package org.sidiff.editrule.generator.serge.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx.NodeKindSelection;
import org.sidiff.editrule.generator.exceptions.OperationTypeNotImplementedException;
import org.sidiff.editrule.generator.types.OperationType;

public class TypeReplacer {

	/**
	 * The original module that needs to be examined for replacables.
	 */
	private Module originalModule;
	
	/**
	 * The operaiton type of the originalModule
	 */
	private OperationType opType;
	
	/**
	 * If super type reduction for the given operation type is preferred for dangling mandatories
	 */
	private Boolean reduceToSuperType;
	
	/**
	 * The EClassifierInfoManagement.
	 */
	private EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();	
	
	/**
	 * Constructor
	 */
	public TypeReplacer(Module originalModule, OperationType opType, boolean reduceToSuperType) throws OperationTypeNotImplementedException {
		this.originalModule = originalModule;	
		this.opType 	= opType;
		this.reduceToSuperType  = reduceToSuperType;
		ECM = EClassifierInfoManagement.getInstance();
	}
	
	
	public Set<Module> replace() throws OperationTypeNotImplementedException {
	
		Set<Module> modules = new HashSet<Module>();
	
		ReplacementTable rT = new ReplacementTable(originalModule);
		
		if(rT.hasReplacements()) {
			
			// print table for module
			rT.printTable();

			// create copy module for each row
			for(ReplacementTableRow row: rT.getRows()) {

				//  create a copy
				Module copy = EcoreUtil.copy(originalModule);

				// iterate over all replacement entries in row
				for(EClassifier replacement: row) {
					Node originalNode = rT.getHeader().get(row.getIndexOf(replacement));

					// find and replace type of corresponding node in copy
					Rule rule = (Rule) copy.getUnits().get(0);
					Node nodeInCopy = HenshinRuleAnalysisUtilEx.getNodeByName(rule, originalNode.getName(), false);

					nodeInCopy.setType((EClass)replacement);

					// create mandatories that might me necessary after replacements
					EClassifierInfo replacementInfo = ECM.getEClassifierInfo(replacement);
					ModuleInternalsApplicator.createMandatoryChildren(rule, replacementInfo, nodeInCopy, opType, reduceToSuperType);
					ModuleInternalsApplicator.createMandatoryNeighbours(rule, replacementInfo, nodeInCopy, opType, reduceToSuperType);

					//Set a new name for this variant module (e.g. blabla_Variant1234)
					long id = System.nanoTime();				
					if(copy.getName().matches(".*(_Variant\\d*\\w*)$")) {
						copy.setDescription(copy.getDescription().replaceAll("(Variant\\d*)$", "Variant"+String.valueOf(id)));
						copy.setName(copy.getName().replaceAll("(Variant\\d*)$", "Variant"+String.valueOf(id)));
					}else{
						copy.setDescription(copy.getDescription()+" Variant"+id);
						copy.setName(copy.getName()	+"_Variant"+id);			
					}

					// check if there are new replacables resulting from mandatory additions
					// and recursivly start replacement process
					List<Node> nodesAfterMandatoryAdditons =
							HenshinRuleAnalysisUtilEx
							.getChildNodesWithinAContainmentRelation(copy, NodeKindSelection.CREATE, false);

					for(Node node: nodesAfterMandatoryAdditons) {
						boolean isNewNode = (HenshinRuleAnalysisUtilEx.getNodeByName((Rule)originalModule.getUnits().get(0), node.getName(), false)==null);
						boolean hasReplacables = !ECM.getAllSubTypes(node.getType()).isEmpty();
						if(isNewNode && hasReplacables) {
							
							TypeReplacer newTypeReplacer = new TypeReplacer(copy, opType, reduceToSuperType);
							modules.addAll(newTypeReplacer.replace());
				
						}
					}
				
					// add module to result list
					modules.add(copy);
				}			
			}	
		}
		return modules;
	}
}

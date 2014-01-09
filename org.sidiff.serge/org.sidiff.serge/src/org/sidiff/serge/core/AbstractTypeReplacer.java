package org.sidiff.serge.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.serge.core.Configuration.OperationType;
import org.sidiff.serge.exceptions.OperationTypeNotImplementedException;

public class AbstractTypeReplacer {

	/**
	 * The original module that needs to be examined for replacables.
	 */
	private Module origModule = null;
	
	/**
	 * The set containing the generated variants for the original module
	 */
	private Set<Module> variantModules = new HashSet<Module>();
	
	/**
	 * The EClassifierInfoManagement.
	 */
	private static EClassifierInfoManagement ecm = EClassifierInfoManagement.getInstance();
	
	/**
	 * Constructor
	 * @param originalModule
	 */
	public AbstractTypeReplacer (Module originalModule){
		this.origModule = origModule;
	}
	
	/**
	 * This method replaces all abstract types of nodes with possible concrete types.
	 * Only abstract nodes are considered that are targeted by an edge with a containment EReference type.
	 * @param
	 * 		 Needs to know what operation type this module considers (create or move e.g.)
	 * @param
	 * 		 Needs to know wheather preferSuperTypes is set for this operation type.
	 * @return
	 * 		  A set of possible modules that do not contain any abstract child nodes anymore.
	 * @throws OperationTypeNotImplementedException 
	 */
	public Set<Module> generateModulesByAbstractChildNodeReplaces(OperationType opType, Boolean preferSupertypes) throws OperationTypeNotImplementedException {
		
		List<Node> allAbstractChildNodesInOriginal = new ArrayList<Node>();
		List<Module> intermediateResults		   = new ArrayList<Module>();
						
		allAbstractChildNodesInOriginal.addAll(
				HenshinRuleAnalysisUtilEx.getChildNodesWithinAContainmentRelation(
				origModule, HenshinRuleAnalysisUtilEx.NodeKindSelection.CREATE,true));
			
		
		for(Node abstractChildNodeInOriginal: allAbstractChildNodesInOriginal) {
			
			// the child that needs replacement
			EClassifier replaceable = abstractChildNodeInOriginal.getType();
			// the list of possible replacements
			List<EClassifier> replacements = ecm.getAllConcreteEClassifiersForAbstract(replaceable);
			
			for(EClassifier replacement: replacements) {
				
				// check if replacement is allowed at all
				if (!ElementFilter.getInstance().isAllowedAsDangling(replacement, opType, preferSupertypes)) continue;				
				System.out.println("Replacing: "+ replaceable.getName() + " in "+origModule.getName()+" with " + replacement.getName());
				
				// create copy
				Module copy = EcoreUtil.copy(origModule);
				
				// adjust moduleName				
				//TODO variant module name regex
				
				// get abstract child nodes of the copy
				List<Node> abstractChildNodesInCopy = HenshinRuleAnalysisUtilEx.getChildNodesWithinAContainmentRelation(
						copy, HenshinRuleAnalysisUtilEx.NodeKindSelection.CREATE,true);
				
				
				// search for the 1st node in copy with same type as replaceable and replace
				for(Node copyNode :abstractChildNodesInCopy) {
					if(copyNode.getType().equals(replaceable)) {
						copyNode.setType((EClass) replacement);
						
						// create mandatories for replacement, if any
						Common.createMandatoryChildren((Rule)copyNode.getGraph().eContainer(),
													ecm.getEClassifierInfo(replacement),
													copyNode, opType, preferSupertypes);
						Common.createMandatoryNeighbours((Rule)copyNode.getGraph().eContainer(),
													ecm.getEClassifierInfo(replacement),
													copyNode, opType, preferSupertypes);
						
						// break, since we want a new module for each possible replacement
						// for each replacable
						break;
					}
				}				
				intermediateResults.add(copy);
			}					
		}
		
		return variantModules;			
	}
	
}

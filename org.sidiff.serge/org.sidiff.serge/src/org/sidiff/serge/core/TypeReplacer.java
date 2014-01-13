package org.sidiff.serge.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.emf.extensions.impl.EClassifierInfo;
import org.sidiff.common.emf.extensions.impl.EClassifierInfoManagement;
import org.sidiff.common.emf.extensions.impl.EcoreHelper;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;

public class TypeReplacer {
	/**
	 * The original module that needs to be examined for replacables.
	 */
	private Module originalModule = null;
	
	/**
	 * The EClassifierInfoManagement.
	 */
	private static EClassifierInfoManagement ECM = EClassifierInfoManagement.getInstance();
	
	/**
	 * Constructor
	 * @param originalModule
	 */
	public TypeReplacer(Module originalModule) {
		this.originalModule = originalModule;

	}
	
	public Set<Module> replace() {
		
		Set<Module> modules = new HashSet<Module>();
		
		List<Node> possibleReplacables = HenshinRuleAnalysisUtilEx.getChildNodesWithinAContainmentRelation(
				originalModule, HenshinRuleAnalysisUtilEx.NodeKindSelection.CREATE,false);
		
		for(Node possibleReplacable: possibleReplacables) {

			EClassifier typeOfReplacable = possibleReplacable.getType();
			
			ArrayList<EClassifier> abstractReplacements = new ArrayList<EClassifier>();
			Set<EClassifier> superTypeReplacements = ECM.getAllSubTypesAsEClassifiers(typeOfReplacable);
			
			if(typeOfReplacable instanceof EClass && ((EClass)typeOfReplacable).isAbstract()) {
				abstractReplacements = ECM.getAllConcreteEClassifiersForAbstract(typeOfReplacable);
			}

			Set<EClassifier> allReplacements = new HashSet<EClassifier>();
			allReplacements.addAll(abstractReplacements);
			allReplacements.addAll(superTypeReplacements);
			
			//TODO...continue build matrix
			
			
			
		}
		
		return modules;
		
	}
}

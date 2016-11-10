package org.sidiff.difference.lifting.recognitionengine.rules;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinMultiRuleAnalysis;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.difference.symmetric.SymmetricPackage;

public class RecognitionRuleBlueprint {
	
	private static final SymmetricPackage SYMMETRIC_PACKAGE = SymmetricPackage.eINSTANCE;

	public Rule recognitionRule;
	
	public Map<EClass, Integer> addObject = new HashMap<EClass, Integer>();
	public Map<EClass, Integer> removeObject = new HashMap<EClass, Integer>();
	public Map<EReference, Integer> addReference = new HashMap<EReference, Integer>();
	public Map<EReference, Integer> removeReference = new HashMap<EReference, Integer>();
	public Map<EAttribute, Integer> attributeValueChange = new HashMap<EAttribute, Integer>();
	
	public Set<EClass> modelTypes = new HashSet<EClass>();
	
	public RecognitionRuleBlueprint(Rule rr) {
		addToBlueprint(rr.getLhs().getNodes());
		HenshinRuleAnalysisUtilEx.getForbidNodes(rr);
		HenshinRuleAnalysisUtilEx.getRequireNodes(rr);
		this.recognitionRule = rr; 
	}
	
	public void appendMultiRules() {
		for (Rule rrMulti : recognitionRule.getMultiRules()) {
			if (rrMulti instanceof Rule) {
				addToBlueprint(HenshinMultiRuleAnalysis.getMultiRuleNodes(rrMulti.getLhs()));
				HenshinRuleAnalysisUtilEx.getForbidNodes(rrMulti);
				HenshinRuleAnalysisUtilEx.getRequireNodes(rrMulti);
			}
		}
	}
	
	private void addToBlueprint(Collection<Node> rrNodes){
		
		for (Node node : rrNodes) {

			// AddObject:
			if (node.getType() == SYMMETRIC_PACKAGE.getAddObject()) {
				EClass type = (EClass) RecognitionRuleUtil.getChangeType(node);
				
				if (addObject.containsKey(type)) {
					addObject.put(type, addObject.get(type) + 1);
				} else {
					addObject.put(type, 1);
				}
			}

			// RemoveObject:
			else if (node.getType() == SYMMETRIC_PACKAGE.getRemoveObject()) {
				EClass type = (EClass) RecognitionRuleUtil.getChangeType(node);
				
				if (removeObject.containsKey(type)) {
					removeObject.put(type, removeObject.get(type) + 1);
				} else {
					removeObject.put(type, 1);
				}
			}

			// AddReference:
			else if (node.getType() == SYMMETRIC_PACKAGE.getAddReference()) {
				EReference type = (EReference) RecognitionRuleUtil.getChangeType(node);
				
				if (addReference.containsKey(type)) {
					addReference.put(type, addReference.get(type) + 1);
				} else {
					addReference.put(type, 1);
				}
			}

			// RemoveReference:
			else if (node.getType() == SYMMETRIC_PACKAGE.getRemoveReference()) {
				EReference type = (EReference) RecognitionRuleUtil.getChangeType(node);
				
				if (removeReference.containsKey(type)) {
					removeReference.put(type, removeReference.get(type) + 1);
				} else {
					removeReference.put(type, 1);
				}
			}

			// AttributeValueChange:
			else if (node.getType() == SYMMETRIC_PACKAGE.getAttributeValueChange()) {
				EAttribute type = (EAttribute) RecognitionRuleUtil.getChangeType(node);
				
				if (attributeValueChange.containsKey(type)) {
					attributeValueChange.put(type, attributeValueChange.get(type) + 1);
				} else {
					attributeValueChange.put(type, 1);
				}
			}
			
			// Model element:
			else {
				modelTypes.add(node.getType());
			}
		}
	}
}

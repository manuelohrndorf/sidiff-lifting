package org.sidiff.difference.lifting.recognitionengine.ruleapplication;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.difference.symmetric.SymmetricPackage;

public class RecognitionRuleGraphBlueprint {

	private static final SymmetricPackage SYM = SymmetricPackage.eINSTANCE;

	public Set<EClass> addObject = new HashSet<EClass>();
	public Set<EClass> removeObject = new HashSet<EClass>();
	public Set<String> addReference = new HashSet<String>();
	public Set<String> removeReference = new HashSet<String>();
	public Set<String> attrValueChange = new HashSet<String>();

	public Set<EClass> modelTypes = new HashSet<EClass>();
	
	public RecognitionRuleGraphBlueprint(Rule rr) {
		addToBlueprint(rr);
		for (Rule mr : rr.getAllMultiRules()) {
			addToBlueprint(mr);
		}
	}

	private void addToBlueprint(Rule rr){
		for (Node node : rr.getLhs().getNodes()) {

			// AddObject
			if (node.getType() == SYM.getAddObject()) {
				EClass type = node.getOutgoing(SYM.getAddObject_Obj()).get(0).getTarget().getType();
				addObject.add(type);
			}

			// RemoveObject
			else if (node.getType() == SYM.getRemoveObject()) {
				EClass type = node.getOutgoing(SYM.getRemoveObject_Obj()).get(0).getTarget().getType();
				removeObject.add(type);
			}

			// AddReference
			else if (node.getType() == SYM.getAddReference()) {
				Node n = node.getOutgoing(SYM.getAddReference_Type()).get(0).getTarget();
				String type = n.getAttribute(EcorePackage.eINSTANCE.getENamedElement_Name()).getValue()
						.replace('\"', ' ').trim();
				addReference.add(type);
			}

			// RemoveReference
			else if (node.getType() == SYM.getRemoveReference()) {
				Node n = node.getOutgoing(SYM.getRemoveReference_Type()).get(0).getTarget();
				String type = n.getAttribute(EcorePackage.eINSTANCE.getENamedElement_Name()).getValue()
						.replace('\"', ' ').trim();
				removeReference.add(type);
			}

			// AttrValueChange
			else if (node.getType() == SYM.getAttributeValueChange()) {
				Node n = node.getOutgoing(SYM.getAttributeValueChange_Type()).get(0).getTarget();
				String type = n.getAttribute(EcorePackage.eINSTANCE.getENamedElement_Name()).getValue()
						.replace('\"', ' ').trim();
				attrValueChange.add(type);
			}
			
			else {
				modelTypes.add(node.getType());
			}
		}
				
		// NACs
		for (Node node : HenshinRuleAnalysisUtilEx.getForbidNodes(rr)) {
			modelTypes.add(node.getType());
		}
		
		// PACs
		for (Node node : HenshinRuleAnalysisUtilEx.getRequireNodes(rr)) {
			modelTypes.add(node.getType());
		}
	}
}

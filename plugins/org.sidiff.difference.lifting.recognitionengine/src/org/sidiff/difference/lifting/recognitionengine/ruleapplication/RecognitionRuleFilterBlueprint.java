package org.sidiff.difference.lifting.recognitionengine.ruleapplication;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.difference.symmetric.SymmetricPackage;

public class RecognitionRuleFilterBlueprint {

	private static final SymmetricPackage SYM = SymmetricPackage.eINSTANCE;

	public Map<EClass, Integer> addObject = new HashMap<EClass, Integer>();
	public Map<EClass, Integer> removeObject = new HashMap<EClass, Integer>();
	public Map<String, Integer> addReference = new HashMap<String, Integer>();
	public Map<String, Integer> removeReference = new HashMap<String, Integer>();
	public Map<String, Integer> attrValueChange = new HashMap<String, Integer>();	
	
	public RecognitionRuleFilterBlueprint(Rule rr) {
		addToBlueprint(rr);
	}

	private void addToBlueprint(Rule rr){
		for (Node node : rr.getLhs().getNodes()) {

			// AddObject
			if (node.getType() == SYM.getAddObject()) {
				EClass type = node.getOutgoing(SYM.getAddObject_Obj()).get(0).getTarget().getType();
				if (addObject.containsKey(type)) {
					addObject.put(type, addObject.get(type) + 1);
				} else {
					addObject.put(type, 1);
				}
			}

			// RemoveObject
			else if (node.getType() == SYM.getRemoveObject()) {
				EClass type = node.getOutgoing(SYM.getRemoveObject_Obj()).get(0).getTarget().getType();
				if (removeObject.containsKey(type)) {
					removeObject.put(type, removeObject.get(type) + 1);
				} else {
					removeObject.put(type, 1);
				}
			}

			// AddReference
			else if (node.getType() == SYM.getAddReference()) {
				Node n = node.getOutgoing(SYM.getAddReference_Type()).get(0).getTarget();
				String type = n.getAttribute(EcorePackage.eINSTANCE.getENamedElement_Name()).getValue()
						.replace('\"', ' ').trim();
				if (addReference.containsKey(type)) {
					addReference.put(type, addReference.get(type) + 1);
				} else {
					addReference.put(type, 1);
				}
			}

			// RemoveReference
			else if (node.getType() == SYM.getRemoveReference()) {
				Node n = node.getOutgoing(SYM.getRemoveReference_Type()).get(0).getTarget();
				String type = n.getAttribute(EcorePackage.eINSTANCE.getENamedElement_Name()).getValue()
						.replace('\"', ' ').trim();
				if (removeReference.containsKey(type)) {
					removeReference.put(type, removeReference.get(type) + 1);
				} else {
					removeReference.put(type, 1);
				}
			}

			// AttrValueChange
			else if (node.getType() == SYM.getAttributeValueChange()) {
				Node n = node.getOutgoing(SYM.getAttributeValueChange_Type()).get(0).getTarget();
				String type = n.getAttribute(EcorePackage.eINSTANCE.getENamedElement_Name()).getValue()
						.replace('\"', ' ').trim();
				if (attrValueChange.containsKey(type)) {
					attrValueChange.put(type, attrValueChange.get(type) + 1);
				} else {
					attrValueChange.put(type, 1);
				}
			}
		}
	}
}

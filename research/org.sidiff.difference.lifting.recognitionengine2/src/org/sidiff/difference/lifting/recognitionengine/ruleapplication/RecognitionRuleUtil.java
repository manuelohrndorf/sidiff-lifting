package org.sidiff.difference.lifting.recognitionengine.ruleapplication;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.difference.symmetric.SymmetricPackage;

public class RecognitionRuleUtil {

	private static final SymmetricPackage SYMMETRIC_PACKAGE = SymmetricPackage.eINSTANCE;
	
	private static final EAttribute E_NAMED_ELEMENT_NAME = EcorePackage.eINSTANCE.getENamedElement_Name();
	
	public static boolean isChangeType(EClass type) {
		
		if ((type == SYMMETRIC_PACKAGE.getAddObject())
				|| (type == SYMMETRIC_PACKAGE.getRemoveObject())
				|| (type == SYMMETRIC_PACKAGE.getAddReference())
				|| (type == SYMMETRIC_PACKAGE.getRemoveReference())
				|| (type == SYMMETRIC_PACKAGE.getAttributeValueChange())) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isChangeNode(Node node) {
		
		if ((node.getType() == SYMMETRIC_PACKAGE.getAddObject())
			|| (node.getType() == SYMMETRIC_PACKAGE.getRemoveObject())
			|| (node.getType() == SYMMETRIC_PACKAGE.getAddReference())
			|| (node.getType() == SYMMETRIC_PACKAGE.getRemoveReference())
			|| (node.getType() == SYMMETRIC_PACKAGE.getAttributeValueChange())) {
			return true;
		}

		return false;
	}
	
	public static EObject getChangeType(Node node) {
		
		// AddObject:
		if (node.getType() == SYMMETRIC_PACKAGE.getAddObject()) {
			return node.getOutgoing(SYMMETRIC_PACKAGE.getAddObject_Obj()).get(0).getTarget().getType();
		}

		// RemoveObject:
		else if (node.getType() == SYMMETRIC_PACKAGE.getRemoveObject()) {
			return node.getOutgoing(SYMMETRIC_PACKAGE.getRemoveObject_Obj()).get(0).getTarget().getType();
		}
		
		// AddReference:
		else if (node.getType() == SYMMETRIC_PACKAGE.getAddReference()) {
			Node n = node.getOutgoing(SYMMETRIC_PACKAGE.getAddReference_Type()).get(0).getTarget();
			String type = n.getAttribute(E_NAMED_ELEMENT_NAME).getValue().replace('\"', ' ').trim();
			
			return (EReference) node.getType().getEStructuralFeature(type);
		}

		// RemoveReference:
		else if (node.getType() == SYMMETRIC_PACKAGE.getRemoveReference()) {
			Node n = node.getOutgoing(SYMMETRIC_PACKAGE.getRemoveReference_Type()).get(0).getTarget();
			String type = n.getAttribute(E_NAMED_ELEMENT_NAME).getValue().replace('\"', ' ').trim();
			
			return (EReference) node.getType().getEStructuralFeature(type);
		}
		
		// AttributeValueChange:
		else if (node.getType() == SYMMETRIC_PACKAGE.getAttributeValueChange()) {
			Node n = node.getOutgoing(SYMMETRIC_PACKAGE.getAttributeValueChange_Type()).get(0).getTarget();
			String type = n.getAttribute(E_NAMED_ELEMENT_NAME).getValue().replace('\"', ' ').trim();
			
			return (EAttribute) node.getType().getEStructuralFeature(type);
		}
		
		return null;
	}
}

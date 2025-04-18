package org.sidiff.difference.lifting.recognitionengine.rules;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricPackage;

public class RecognitionRuleUtil {

	private static final SymmetricPackage SYMMETRIC_PACKAGE = SymmetricPackage.eINSTANCE;
	
	private static final EAttribute E_NAMED_ELEMENT_NAME = EcorePackage.eINSTANCE.getENamedElement_Name();
	
	public static SymmetricDifference getSymmetricDifference(ResourceSet rss) {
		SymmetricDifference difference = null;
		
		for (Resource res : rss.getResources()) {
			if ((res.getContents() != null) && (res.getContents().get(0) instanceof SymmetricDifference)) {
				difference = (SymmetricDifference) res.getContents().get(0);
				break;
			}
		}
		
		return difference;
	}
	
	public static boolean isChangeNode(Node node) {
		
		if (node.getType() == SYMMETRIC_PACKAGE.getAddObject()
				|| (node.getType() == SYMMETRIC_PACKAGE.getRemoveObject())
				|| (node.getType() == SYMMETRIC_PACKAGE.getAddReference())
				|| (node.getType() == SYMMETRIC_PACKAGE.getRemoveReference())
				|| (node.getType() == SYMMETRIC_PACKAGE.getAttributeValueChange())) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isTypeNode(Node node) {
		
		for (Edge outgoing : node.getOutgoing()) {
			if ( (outgoing.getType() == SYMMETRIC_PACKAGE.getAddReference_Type())
					|| (outgoing.getType() == SYMMETRIC_PACKAGE.getRemoveReference_Type())
					|| (outgoing.getType() == SYMMETRIC_PACKAGE.getAttributeValueChange_Type())) {
				return true;
			}
		}
		
		return false;
	}
	
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
	
	public static boolean isChangeReference(EReference type) {
		
		if ((type == SYMMETRIC_PACKAGE.getAddObject_Obj())
				|| (type == SYMMETRIC_PACKAGE.getRemoveObject_Obj())
				|| (type == SYMMETRIC_PACKAGE.getAddReference_Src())
				|| (type == SYMMETRIC_PACKAGE.getAddReference_Tgt())
				|| (type == SYMMETRIC_PACKAGE.getRemoveReference_Src())
				|| (type == SYMMETRIC_PACKAGE.getRemoveReference_Tgt())
				|| (type == SYMMETRIC_PACKAGE.getAttributeValueChange_ObjA())
				|| (type == SYMMETRIC_PACKAGE.getAttributeValueChange_ObjB())) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isChangeTypeReference(EReference type) {
		
		if ((type == SYMMETRIC_PACKAGE.getAddReference_Type())
				|| (type == SYMMETRIC_PACKAGE.getRemoveReference_Type())
				|| (type == SYMMETRIC_PACKAGE.getAttributeValueChange_Type())) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param node
	 *            A change node.
	 * @return The type, i.e. an EClass, EReference or EAttribute.
	 */
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
			Node typeNode = node.getOutgoing(SYMMETRIC_PACKAGE.getAddReference_Type()).get(0).getTarget();
			String typeName = typeNode.getAttribute(E_NAMED_ELEMENT_NAME).getValue().replace('\"', ' ').trim();
			Node srcNode = node.getOutgoing(SYMMETRIC_PACKAGE.getAddReference_Src()).get(0).getTarget();
			
			return srcNode.getType().getEStructuralFeature(typeName);
		}

		// RemoveReference:
		else if (node.getType() == SYMMETRIC_PACKAGE.getRemoveReference()) {
			Node typeNode = node.getOutgoing(SYMMETRIC_PACKAGE.getRemoveReference_Type()).get(0).getTarget();
			String typeName = typeNode.getAttribute(E_NAMED_ELEMENT_NAME).getValue().replace('\"', ' ').trim();
			Node srcNode = node.getOutgoing(SYMMETRIC_PACKAGE.getRemoveReference_Src()).get(0).getTarget();

			return srcNode.getType().getEStructuralFeature(typeName);
		}

		// AttributeValueChange:
		else if (node.getType() == SYMMETRIC_PACKAGE.getAttributeValueChange()) {
			Node typeNode = node.getOutgoing(SYMMETRIC_PACKAGE.getAttributeValueChange_Type()).get(0).getTarget();
			String typeName = typeNode.getAttribute(E_NAMED_ELEMENT_NAME).getValue().replace('\"', ' ').trim();
			Node aNode = node.getOutgoing(SYMMETRIC_PACKAGE.getAttributeValueChange_ObjA()).get(0).getTarget();
			
			return aNode.getType().getEStructuralFeature(typeName);
		}
		
		return null;
	}
}

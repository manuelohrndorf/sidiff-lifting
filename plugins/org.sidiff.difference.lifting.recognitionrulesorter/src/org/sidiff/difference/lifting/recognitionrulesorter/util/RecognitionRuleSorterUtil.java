package org.sidiff.difference.lifting.recognitionrulesorter.util;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.difference.symmetric.SymmetricPackage;
import org.sidiff.matching.model.MatchingModelPackage;

public class RecognitionRuleSorterUtil {
	
	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is a Difference-Node;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isDifference(Node node) {
		return node.getType() == SymmetricPackage.eINSTANCE.getSymmetricDifference();
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is a Type-Node; <code>false</code>
	 *         otherwise.
	 */
	public static boolean isTypeNode(Node node) {
		if (node.getAttributes().size() != 1) {
			return false;
		}

		if ((node.getType() == EcorePackage.eINSTANCE.getEReference())
				|| (node.getType() == EcorePackage.eINSTANCE.getEAttribute())) {
			// We also need to find an incoming edge according to symmetric difference model:
			
			// ... try to find it in Kernel Rule
			if (isUsedAsTypeNodeInChange(node)){
				return true;
			}
			
			// ... try to find it in multi-rules
			Rule kernelRule = node.getGraph().getRule();
			for (Rule multiRule : kernelRule.getMultiRules()) {
				Node image = multiRule.getMultiMappings().getImage(node, multiRule.getLhs());
				if (isUsedAsTypeNodeInChange(image)){
					return true;
				}
				image = multiRule.getMultiMappings().getImage(node, multiRule.getRhs());
				if (isUsedAsTypeNodeInChange(image)){
					return true;
				}
			}
		}

		return false;
	}

	private static boolean isUsedAsTypeNodeInChange(Node node){
		if (node == null) {
			return false;
		}
		
		for (Edge edge : node.getIncoming()) {
			if ((edge.getType() == SymmetricPackage.eINSTANCE.getAttributeValueChange_Type())
					|| (edge.getType() == SymmetricPackage.eINSTANCE.getAddReference_Type())
					|| (edge.getType() == SymmetricPackage.eINSTANCE.getRemoveReference_Type())) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the (sub-)type Change;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isChangeNode(Node node) {
		return node.getType().getESuperTypes().contains(SymmetricPackage.eINSTANCE.getChange())
				|| node.getType() == SymmetricPackage.eINSTANCE.getChange();
	}
	
	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type AddObject;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isAddObject(Node node) {
		return node.getType() == SymmetricPackage.eINSTANCE.getAddObject();
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type RemoveObject;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isRemoveObject(Node node) {
		return node.getType() == SymmetricPackage.eINSTANCE.getRemoveObject();
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type AddReference;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isAddReference(Node node) {
		return node.getType() == SymmetricPackage.eINSTANCE.getAddReference();
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type RemoveReference;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isRemoveReference(Node node) {
		return node.getType() == SymmetricPackage.eINSTANCE.getRemoveReference();
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type
	 *         AttributeValueChange; <code>false</code> otherwise.
	 */
	public static boolean isAttributeValueChange(Node node) {
		return node.getType() == SymmetricPackage.eINSTANCE.getAttributeValueChange();
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type Correspondence;
	 *         <code>false</code> otherwise.
	 */
	public static boolean isCorrespondence(Node node) {
		return node.getType() == MatchingModelPackage.eINSTANCE.getCorrespondence();
	}
}

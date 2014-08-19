package org.sidiff.difference.lifting.recognitionengine.ruleapplication;

import java.util.Comparator;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.difference.symmetric.SymmetricPackage;
import org.sidiff.difference.symmetric.util.DifferenceAnalysis;

/**
 * Sorts a Henshin recognition rule to be optimized for matching on a technical
 * difference. The Type-Nodes and Change-Nodes will be moved and ordered to the
 * top of the graph and the correspondences will be moved to the bottom of the
 * graph list. All Model-Nodes will retain in the middle of the graph list. The
 * Change-Nodes are ordered by the count of their appearance in the difference.
 * That means atomic changes with a low count in the difference will appear on
 * the top the Henshin graph list and will be matched first. This is important
 * to get a "good" execution time.
 */
public class RecognitionRuleSorter implements Comparator<Node> {

	/**
	 * The corresponding difference analysis knowing the count of changes.
	 */
	private DifferenceAnalysis analysis;

	/**
	 * Initialize rule sorter.
	 * 
	 * @param analysis
	 *            the corresponding difference analysis knowing the count of
	 *            changes in the difference.
	 */
	public RecognitionRuleSorter(DifferenceAnalysis analysis) {
		super();
		this.analysis = analysis;
	}

	@Override
	public int compare(Node n1, Node n2) {

		int value1 = 0;
		int value2 = 0;

		// Move Difference always to the top
		if (isDifference(n1)) {
			return -1;
		}
		else if (isDifference(n2)) {
			return 1;
		}

		// Move Correspondences always to the bottom
		if (isCorrespondence(n1) && isCorrespondence(n2)) {
			return 0;
		}
		else if (isCorrespondence(n1)) {
			return 1;
		}
		else if (isCorrespondence(n2)) {
			return -1;
		}

		// Move Type-Nodes always to the top
		if (isTypeNode(n1) && isTypeNode(n2)) {
			return 0;
		}
		else if (isTypeNode(n1)) {
			return -1;
		}
		else if (isTypeNode(n2)) {
			return 1;
		}

		// Set values for Change-Nodes
		value1 = getChangeNodeValue(n1);
		value2 = getChangeNodeValue(n2);

		// Sort Model-Nodes between Change-Nodes and Correspondences
		if ((value1 == -1) && (value2 == -1)) {
			return 0;
		}
		else if ((value1 == -1)) {
			return 1;
		}
		else if ((value2 == -1)) {
			return -1;
		}

		return value1 - value2;
	}

	/**
	 * Get the object of the Change-Node count in the difference.
	 * 
	 * @param node
	 *            the Change-Node.
	 * @return the object count of the Change-Node count in the difference.
	 */
	private int getChangeNodeValue(Node node) {

		if (isAddObject(node)) {
			return analysis.getAddObjectCount();
		}
		else if (isRemoveObject(node)) {
			return analysis.getRemoveObjectCount();
		}
		else if (isAttributeValueChange(node)) {
			return analysis.getAttributeValueChangeCount();
		}
		else if (isAddReference(node)) {
			return analysis.getAddReferenceCount();
		}
		else if (isRemoveReference(node)) {
			return analysis.getRemoveReferenceCount();
		}
		else {
			return -1;
		}
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is a Difference-Node;
	 *         <code>false</code> otherwise.
	 */
	private boolean isDifference(Node node) {
		if (node.getType() == SymmetricPackage.eINSTANCE.getSymmetricDifference()) {
			return true;
		}
		return false;
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is a Type-Node; <code>false</code>
	 *         otherwise.
	 */
	private boolean isTypeNode(Node node) {

		if (node.getAttributes().size() != 1) {
			return false;
		}

		if ((node.getType() == EcorePackage.eINSTANCE.getEReference())
				|| (node.getType() == EcorePackage.eINSTANCE.getEAttribute())) {
			for (Edge edge : node.getIncoming()) {
				if ((edge.getType() == SymmetricPackage.eINSTANCE
						.getAttributeValueChange_Type())
						|| (edge.getType() == SymmetricPackage.eINSTANCE
								.getAddReference_Type())
						|| (edge.getType() == SymmetricPackage.eINSTANCE
								.getRemoveReference_Type())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type AddObject;
	 *         <code>false</code> otherwise.
	 */
	private boolean isAddObject(Node node) {
		if (node.getType() == SymmetricPackage.eINSTANCE.getAddObject()) {
			return true;
		}
		return false;
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type RemoveObject;
	 *         <code>false</code> otherwise.
	 */
	private boolean isRemoveObject(Node node) {
		if (node.getType() == SymmetricPackage.eINSTANCE.getRemoveObject()) {
			return true;
		}
		return false;
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type AddReference;
	 *         <code>false</code> otherwise.
	 */
	private boolean isAddReference(Node node) {
		if (node.getType() == SymmetricPackage.eINSTANCE.getAddReference()) {
			return true;
		}
		return false;
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type RemoveReference;
	 *         <code>false</code> otherwise.
	 */
	private boolean isRemoveReference(Node node) {
		if (node.getType() == SymmetricPackage.eINSTANCE.getRemoveReference()) {
			return true;
		}
		return false;
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type
	 *         AttributeValueChange; <code>false</code> otherwise.
	 */
	private boolean isAttributeValueChange(Node node) {
		if (node.getType() == SymmetricPackage.eINSTANCE.getAttributeValueChange()) {
			return true;
		}
		return false;
	}

	/**
	 * Test node type.
	 * 
	 * @param node
	 *            the Henshin node to test
	 * @return <code>true</code> if the node is of the type Correspondence;
	 *         <code>false</code> otherwise.
	 */
	private boolean isCorrespondence(Node node) {
		if (node.getType() == SymmetricPackage.eINSTANCE.getCorrespondence()) {
			return true;
		}
		return false;
	}
}

package org.sidiff.difference.lifting.recognitionrulesorter;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
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
public abstract class AbstractRecognitionRuleSorter implements IRecognitionRuleSorter {

	/**
	 * The corresponding difference analysis knowing the count of changes.
	 */
	private DifferenceAnalysis analysis;

	@Override
	public int compare(Node n1, Node n2) {

		int value1 = 0;
		int value2 = 0;

		// Move Difference always to the top
		if (isDifference(n1)) {
			return -1;
		} else if (isDifference(n2)) {
			return 1;
		}

		// Move Correspondences always to the bottom
		if (isCorrespondence(n1) && isCorrespondence(n2)) {
			return 0;
		} else if (isCorrespondence(n1)) {
			return 1;
		} else if (isCorrespondence(n2)) {
			return -1;
		}

		// Move Type-Nodes always to the top
		if (isTypeNode(n1) && isTypeNode(n2)) {
			return 0;
		} else if (isTypeNode(n1)) {
			return -1;
		} else if (isTypeNode(n2)) {
			return 1;
		}

		// Set values for Change-Nodes
		value1 = getChangeNodeValue(n1);
		value2 = getChangeNodeValue(n2);

		if ((value1 == -1) && (value2 == -1)) {
			// Two model nodes: delegate their comparison to domain
			// configuration
			return compareModelNodes(n1, n2);
		}
		// Sort Model-Nodes between Change-Nodes and Correspondences
		else if ((value1 == -1)) {
			return 1;
		} else if ((value2 == -1)) {
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
	protected int getChangeNodeValue(Node node) {

		if (isAddObject(node)) {
			return analysis.getAddObjectCount();
		} else if (isRemoveObject(node)) {
			return analysis.getRemoveObjectCount();
		} else if (isAttributeValueChange(node)) {
			return analysis.getAttributeValueChangeCount();
		} else if (isAddReference(node)) {
			return analysis.getAddReferenceCount();
		} else if (isRemoveReference(node)) {
			return analysis.getRemoveReferenceCount();
		} else {
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
	protected boolean isDifference(Node node) {
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
	protected boolean isTypeNode(Node node) {

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

	private boolean isUsedAsTypeNodeInChange(Node node){
		if (node == null){
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
	 * @return <code>true</code> if the node is of the type AddObject;
	 *         <code>false</code> otherwise.
	 */
	protected boolean isAddObject(Node node) {
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
	protected boolean isRemoveObject(Node node) {
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
	protected boolean isAddReference(Node node) {
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
	protected boolean isRemoveReference(Node node) {
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
	protected boolean isAttributeValueChange(Node node) {
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
	protected boolean isCorrespondence(Node node) {
		if (node.getType() == SymmetricPackage.eINSTANCE.getCorrespondence()) {
			return true;
		}
		return false;
	}

	
	// Order of type nodes should be declared by an (optional) domain Plug-In
	protected abstract int compareModelNodes(Node n1, Node n2);


	// Same types: Give node with more attributes the higher priority
	protected int compareNodesOfSameType(Node n1, Node n2){
		return n2.getAttributes().size() - n1.getAttributes().size();
	}
	
	protected boolean is_EStringToStringMapEntry(Node n){
		return n.getType().getName().equals("EStringToStringMapEntry");
	}
	
	/**
	 * @param analysis
	 * 			The corresponding difference analysis knowing the count of changes
	 */
	@Override
	public void setDifferenceAnalysis(DifferenceAnalysis analysis){
		this.analysis = analysis;
	}
	
	/**
	 * @return The corresponding difference analysis knowing the count of changes
	 */
	@Override
	public DifferenceAnalysis getDifferenceAnalysis(){
		return analysis;
	}
}

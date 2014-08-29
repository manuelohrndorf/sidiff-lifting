package org.sidiff.difference.lifting.recognitionengine.ruleapplication;

import java.util.Comparator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
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
	private int getChangeNodeValue(Node node) {

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
				if ((edge.getType() == SymmetricPackage.eINSTANCE.getAttributeValueChange_Type())
						|| (edge.getType() == SymmetricPackage.eINSTANCE.getAddReference_Type())
						|| (edge.getType() == SymmetricPackage.eINSTANCE.getRemoveReference_Type())) {
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

	// TODO: This is actually a configuration option
	// Order of type nodes should be declared by an (optional) domain Plug-In
	private int compareModelNodes(Node n1, Node n2) {
		if (isFM(n1.getType(), n2.getType())) {
			return compareFM(n1, n2);
		} if (isEcore(n1.getType(), n2.getType())) {
			return compareEcore(n1, n2);
		} else {
			return 0;
		}
	}

	private int compareFM(Node n1, Node n2) {
		// (1) FeatureModel
		if (is_FeatureModel(n1) && is_FeatureModel(n2)) {
			return 0;
		} else if (is_FeatureModel(n1)) {
			return -1;
		} else if (is_FeatureModel(n2)) {
			return 1;
		}
		
		// (2) Group
		if (is_Group(n1) && is_Group(n2)) {
			return compareNodesOfSameType(n1,n2);
		} else if (is_Group(n1)) {
			return -1;
		} else if (is_Group(n2)) {
			return 1;
		}
		
		// (3) Feature
		if (is_Feature(n1) && is_Feature(n2)) {
			return compareNodesOfSameType(n1,n2);
		} else if (is_Feature(n1)) {
			return -1;
		} else if (is_Feature(n2)) {
			return 1;
		}
		
		// (4) ExcludeConstraint
		if (is_ExcludeConstraint(n1) && is_ExcludeConstraint(n2)) {
			return compareNodesOfSameType(n1,n2);
		} else if (is_ExcludeConstraint(n1)) {
			return -1;
		} else if (is_ExcludeConstraint(n2)) {
			return 1;
		}
		
		// (5) RequireConstraint
		if (is_RequireConstraint(n1) && is_RequireConstraint(n2)) {
			return compareNodesOfSameType(n1,n2);
		} else if (is_RequireConstraint(n1)) {
			return -1;
		} else if (is_RequireConstraint(n2)) {
			return 1;
		}
		
		assert(false) : "We should never come here";
		return 0;
	}

	// Same types: Give node with more attributes the higher priority
	private int compareNodesOfSameType(Node n1, Node n2){
		return n2.getAttributes().size() - n1.getAttributes().size();
	}
	
	private boolean is_FeatureModel(Node n){
		return n.getType().getName().equals("FeatureModel");
	}
	
	private boolean is_Group(Node n){
		return n.getType().getName().equals("Group");
	}
	
	private boolean is_Feature(Node n){
		return n.getType().getName().equals("Feature");
	}
	
	private boolean is_ExcludeConstraint(Node n){
		return n.getType().getName().equals("ExcludeConstraint");
	}
	
	private boolean is_RequireConstraint(Node n){
		return n.getType().getName().equals("RequireConstraint");
	}
	
	// Check for nsUri
	private boolean isFM(EClass type1, EClass type2) {
		if (!(type1.eContainer() instanceof EPackage) || !(type2.eContainer() instanceof EPackage)) {
			return false;
		}

		EPackage p1 = (EPackage) type1.eContainer();
		EPackage p2 = (EPackage) type2.eContainer();

		return p1.getNsURI().toString().equals("http://de.imotep.variability/featuremodel")
				&& p2.getNsURI().toString().equals("http://de.imotep.variability/featuremodel");
	}
	
	// Check for nsUri
	private boolean isEcore(EClass type1, EClass type2) {
		if (!(type1.eContainer() instanceof EPackage) || !(type2.eContainer() instanceof EPackage)) {
			return false;
		}

		EPackage p1 = (EPackage) type1.eContainer();
		EPackage p2 = (EPackage) type2.eContainer();

		return p1.getNsURI().toString().equals("http://www.eclipse.org/emf/2002/Ecore")
				&& p2.getNsURI().toString().equals("http://www.eclipse.org/emf/2002/Ecore");
	}
	
	private int compareEcore(Node n1, Node n2) {
		// (1) EStringToStringMapEntry ganz nach unten
		if (is_EStringToStringMapEntry(n1) && is_EStringToStringMapEntry(n2)) {
			return 0;
		} else if (is_EStringToStringMapEntry(n1)) {
			return -1;
		} else if (is_EStringToStringMapEntry(n2)) {
			return 1;
		}
		
		return 0;
	}

	private boolean is_EStringToStringMapEntry(Node n){
		return n.getType().getName().equals("EStringToStringMapEntry");
	}
	
}

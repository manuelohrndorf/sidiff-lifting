package org.sidiff.difference.lifting.recognitionrulesorter;

import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isAddObject;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isAddReference;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isAttributeValueChange;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isCorrespondence;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isDifference;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isRemoveObject;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isRemoveReference;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isTypeNode;

import org.eclipse.emf.henshin.model.Node;
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
		int value1 = getChangeNodeValue(n1);
		int value2 = getChangeNodeValue(n2);

		if (value1 == -1 && value2 == -1) {
			// Two model nodes: delegate their comparison to domain
			// configuration
			return compareModelNodes(n1, n2);
		}
		// Sort Model-Nodes between Change-Nodes and Correspondences
		else if (value1 == -1) {
			return 1;
		} else if (value2 == -1) {
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
		}
		return -1;
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

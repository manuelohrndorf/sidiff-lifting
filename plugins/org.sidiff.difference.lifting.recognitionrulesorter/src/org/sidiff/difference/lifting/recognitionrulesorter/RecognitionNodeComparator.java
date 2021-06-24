package org.sidiff.difference.lifting.recognitionrulesorter;

import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isAddObject;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isAddReference;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isAttributeValueChange;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isCorrespondence;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isDifference;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isRemoveObject;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isRemoveReference;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isTypeNode;

import java.util.Comparator;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.difference.symmetric.util.DifferenceAnalysis;

public class RecognitionNodeComparator implements Comparator<Node> {

	/**
	 * The working graph.
	 */
	private EGraph eGraph;

	/**
	 * The difference and statistics.
	 */
	private DifferenceAnalysis analysis;

	/**
	 * @param eGraph   The working graph.
	 * @param analysis The difference and statistics.
	 */
	public RecognitionNodeComparator(EGraph eGraph, DifferenceAnalysis analysis) {
		this.eGraph = eGraph;
		this.analysis = analysis;
	}

	/**
	 * @return The working graph.
	 */
	public EGraph getEGraph() {
		return eGraph;
	}

	/**
	 * @return The difference and statistics.
	 */
	public DifferenceAnalysis getAnalysis() {
		return analysis;
	}

	/**
	 * Order of type nodes can be declared by an (optional) domain Plug-In
	 * 
	 * @param n1 first node.
	 * @param n2 second node.
	 * @return Compares its two nodes for order. Returns a negative integer, zero,
	 *         or a positive integer as the first node index position is less than,
	 *         equal to, or greater than the second.
	 */
	@Override
	public int compare(Node n1, Node n2) {

		// Move Difference always to the top:
		if (isDifference(n1)) {
			return -1;
		} else if (isDifference(n2)) {
			return 1;
		}

		// Move Correspondences always to the bottom:
		if (isCorrespondence(n1) && isCorrespondence(n2)) {
			return compareDomainSize(n1, n2);
		} else if (isCorrespondence(n1)) {
			return 1;
		} else if (isCorrespondence(n2)) {
			return -1;
		}

		// Move Type-Nodes always to the top:
		if (isTypeNode(n1) && isTypeNode(n2)) {
			return 0;
		} else if (isTypeNode(n1)) {
			return -1;
		} else if (isTypeNode(n2)) {
			return 1;
		}

		// Set values for Change-Nodes:
		int value1 = getChangeNodeValue(n1);
		int value2 = getChangeNodeValue(n2);

		if (value1 == -1 && value2 == -1) {
			// Two model nodes:
			return compareModelNodes(n1, n2);
		}
		// Sort Model-Nodes between Change-Nodes and Correspondences:
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
	 * @param node the Change-Node.
	 * @return the object count of the Change-Node count in the difference.
	 */
	protected int getChangeNodeValue(Node node) {
		if (isAddObject(node) || isRemoveObject(node) 
				|| isAddReference(node) || isRemoveReference(node)
				|| isAttributeValueChange(node)) {
			
			// TODO: This give the "aggregated domain size" (see LiftingGraphProxy). 
			//       --> Use real change domain size (see LiftingGraphDomainMap).
			return eGraph.getDomainSize(node.getType(), false);
		}
		return -1;
	}

	/**
	 * Order of type nodes can be declared by an (optional) domain Plug-In
	 * 
	 * @param n1 first node.
	 * @param n2 second node.
	 * @return Compares its two nodes for order. Returns a negative integer, zero,
	 *         or a positive integer as the first node index position is less than,
	 *         equal to, or greater than the second.
	 */
	protected int compareModelNodes(Node n1, Node n2) {
		return compareDomainSize(n1, n2);
	}

	/**
	 * Order of nodes by domain size in working graph.
	 * 
	 * @param n1 first node.
	 * @param n2 second node.
	 * @return Compares its two nodes for order. Returns a negative integer, zero,
	 *         or a positive integer as the first node index position is less than,
	 *         equal to, or greater than the second.
	 */
	protected int compareDomainSize(Node n1, Node n2) {
		return eGraph.getDomainSize(n1.getType(), false) - eGraph.getDomainSize(n2.getType(), false);
	}

}

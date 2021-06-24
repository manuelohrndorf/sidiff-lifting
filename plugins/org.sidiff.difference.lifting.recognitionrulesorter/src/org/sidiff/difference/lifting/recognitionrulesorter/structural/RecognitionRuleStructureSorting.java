package org.sidiff.difference.lifting.recognitionrulesorter.structural;

import static org.sidiff.common.henshin.HenshinMultiRuleAnalysis.isBoundaryNode;
import static org.sidiff.common.henshin.HenshinMultiRuleAnalysis.isEmbeddedNode;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isChangeNode;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isCorrespondence;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Sorts a set of recognition rules by its structure. This will optimize the
 * performance of the Henshin match finder.
 * 
 * @author Manuel Ohrndorf
 */
public class RecognitionRuleStructureSorting {
	
	/**
	 * Sorts a recognition rules by its structure.
	 * 
	 * @param recognitionRules
	 *            The recognition rule to sort.
	 */
	public void sort(Rule recognitionRule) {
		
		// Sort kernel rule
		EList<Node> nodes = recognitionRule.getLhs().getNodes();
		structuredSorting(getStartNode(nodes), nodes);
		
		// Sort application conditions:
		for (NestedCondition ac : recognitionRule.getLhs().getNestedConditions()) {
			structuredSorting(getACStartNode(ac), ac.getConclusion().getNodes());
		}
		
		// Sort all multi-rules (if there are any)
		for (Rule multiRule : recognitionRule.getAllMultiRules()) {
			EList<Node> multiNodes = multiRule.getLhs().getNodes();
			structuredSorting(getMultiStartNode(multiRule), multiNodes);
			
			// Sort application conditions:
			for (NestedCondition ac : multiRule.getLhs().getNestedConditions()) {
				structuredSorting(getACStartNode(ac), ac.getConclusion().getNodes());
			}
		}
	}
	
	protected int getStartNode(EList<Node> nodes) {
		return getStartNode(nodes, 0);
	}
	
	protected int getStartNode(EList<Node> nodes, int offset) {
		
		// Find first change node:
		// (Ignore SymmetricDifference and EReference type nodes.)
		for (int i = offset; i < nodes.size(); i++) {
			if (isChangeNode(nodes.get(i))) {
				return i;
			}
		}
		
		return nodes.size();
	}
	
	protected int getACStartNode(NestedCondition ac) {
		EList<Node> nodes = ac.getConclusion().getNodes();
		int offset = 0;
		
		// Use already matched nodes as starting points:
		for (int i = 0; i < nodes.size(); ++i) {
			Node node =  nodes.get(i);
			
			if (ac.getMappings().getOrigin(node) != null) {
				nodes.move(0, i);
				++offset;
			}
		}
		
		return offset;
	}
	
	protected int getMultiStartNode(Rule multiRule) {
		int embeddedOffset = 0;
		int boundaryOffset = 0;
		EList<Node> nodes = multiRule.getLhs().getNodes();
		
		// Filter embedded nodes:
		for (int i = 0; i < nodes.size(); ++i) {
			Node node = nodes.get(i);
			
			if (isEmbeddedNode(multiRule, node)) {
				nodes.move(0, i);
				++embeddedOffset;
			}
		}
		
		// Get all boundary nodes as start nodes:
		for (int i = embeddedOffset; i < nodes.size(); ++i) {
			Node node = nodes.get(i);
			
			if (isBoundaryNode(multiRule, node)) {
				nodes.move(embeddedOffset + boundaryOffset, i);
				++boundaryOffset;
			}
		}
		
		return getStartNode(nodes, embeddedOffset + boundaryOffset);
	}
	
	protected void structuredSorting(int startIndex, EList<Node> nodes) {
		
		// Get unsorted nodes and their initial sorting:
		LinkedHashSet<Node> unsorted = new LinkedHashSet<>();
		LinkedHashSet<Node> unsortedDiff = new LinkedHashSet<>(); 
		
		for (int i = startIndex; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			
			if (isChangeNode(node) || isCorrespondence(node)) {
				unsortedDiff.add(node);
			} else {
				unsorted.add(node);
			}
		}
		
		// Already sorted head of list:
		List<Node> sortedHead = new ArrayList<>();
				
		for (int i = 0; i < startIndex; i++) {
			Node node = nodes.get(i);
			sortedHead.add(node);
		}
				
		// Do structure sorting:
		LinkedHashSet<Node> sorted = new LinkedHashSet<>();
		
		Node startNode = nodes.get(startIndex);
		sorted.add(startNode);
		unsorted.remove(startNode);
		unsortedDiff.remove(startNode);
		
		while (!unsorted.isEmpty() || !unsortedDiff.isEmpty()) {
			Node nextNode = nextNode(sorted, unsorted, unsortedDiff);
			sorted.add(nextNode);
			unsorted.remove(nextNode);
			unsortedDiff.remove(nextNode);
		}
		
		assert sortedHead.size() + sorted.size() == nodes.size() : "Missing nodes after sorting!";
		
		// Insert new sorting:
		nodes.clear();
		nodes.addAll(sortedHead);
		nodes.addAll(sorted);
	}
	
	protected Node nextNode(LinkedHashSet<Node> sorted, LinkedHashSet<Node> unsorted, LinkedHashSet<Node> unsortedDiff) {
		
		// Find first matching node in list of unsorted:
		
		// (1) Matched Node <- Difference Node (reverse indexed):
		for (Node unsortedNode : unsortedDiff) {
			for (Edge outgoing : unsortedNode.getOutgoing()) {
				Node targetNode = outgoing.getTarget();

				if (sorted.contains(targetNode)) {
					return unsortedNode;
				}
			}
		}
		
		// TODO: Prefer multiplicity [0..1] or [1..1]!?
		
		// (2) Matched Node -> Next Node:
		for (Node unsortedNode : unsorted) {
			for (Edge incoming : unsortedNode.getIncoming()) {
				Node sourceNode = incoming.getSource();

				if (sorted.contains(sourceNode)) {
					return unsortedNode;
				}
			}
		}
		
		// (3) Matched Node <- Next Node:
		for (Node unsortedNode : unsorted) {
			for (Edge outgoing : unsortedNode.getOutgoing()) {
				Node targetNode = outgoing.getTarget();

				if (sorted.contains(targetNode)) {
					return unsortedNode;
				}
			}
		}
		
		// No structural connection found!?
		if (!unsortedDiff.isEmpty()) {
			return unsortedDiff.iterator().next();
		} else {
			return unsorted.iterator().next();
		}
	}
}

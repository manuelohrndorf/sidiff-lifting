package org.sidiff.difference.lifting.recognitionrulesorter.structural;

import static org.sidiff.common.henshin.HenshinMultiRuleAnalysis.isBoundaryNode;
import static org.sidiff.common.henshin.HenshinMultiRuleAnalysis.isEmbeddedNode;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isChangeNode;
import static org.sidiff.difference.lifting.recognitionrulesorter.util.RecognitionRuleSorterUtil.isTypeNode;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
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
	 * Sorts a set of recognition rules by its structure.
	 * 
	 * @param recognitionRules
	 *            All recognition rule to sort.
	 */
	public static void sort(Collection<Rule> recognitionRules) {
		
		for (Rule recognitionRule : recognitionRules) {
			sort(recognitionRule);
		}
	}
	
	/**
	 * Sorts a recognition rules by its structure.
	 * 
	 * @param recognitionRules
	 *            The recognition rule to sort.
	 */
	public static void sort(Rule recognitionRule) {
		
		// Sort kernel rule
		EList<Node> nodes = recognitionRule.getLhs().getNodes();
		SortingConstraint.structuredSorting(RecognitionRuleStructureSorting.getStartNode(nodes), nodes);
		
		// Sort application conditions:
		for (NestedCondition ac : recognitionRule.getLhs().getNestedConditions()) {
			SortingConstraint.structuredSorting(getACStartNode(ac), ac.getConclusion().getNodes());
		}
		
		// Sort all multi-rules (if there are any)
		for (Rule multiRule : recognitionRule.getAllMultiRules()) {
			EList<Node>  multiNodes = multiRule.getLhs().getNodes();
			SortingConstraint.structuredSorting(RecognitionRuleStructureSorting.getMultiStartNode(multiRule), multiNodes);
			
			// Sort application conditions:
			for (NestedCondition ac : multiRule.getLhs().getNestedConditions()) {
				SortingConstraint.structuredSorting(getACStartNode(ac), ac.getConclusion().getNodes());
			}
		}
	}
	
	private static int getStartNode(EList<Node> nodes) {
		return getStartNode(nodes, 0);
	}
	
	private static int getStartNode(EList<Node> nodes, int offset) {
		boolean hasTypeNode = false;
		
		// Find first change node:
		// (Ignore SymmetricDifference and EReference type nodes.)
		for (int i = offset; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			
			// Filter non-structural changes e.g. AVCs:
			if (!hasTypeNode && isTypeNode(node)) {
				hasTypeNode = true;
			} else if (hasTypeNode && isChangeNode(nodes.get(i))) {
				return i;
			}
		}
		
		return nodes.size();
	}
	
	private static int getACStartNode(NestedCondition ac) {
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
	
	
	private static int getMultiStartNode(Rule multiRule) {
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
}

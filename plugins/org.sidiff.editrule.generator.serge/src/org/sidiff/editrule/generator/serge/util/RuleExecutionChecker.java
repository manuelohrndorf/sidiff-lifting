package org.sidiff.editrule.generator.serge.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.metamodel.analysis.EReferenceInfo;
import org.sidiff.common.henshin.ApplicationCondition;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.henshin.view.NodePair;

/**
 * Utility class that can check if a given rule is executable in
 * principal.
 * 
 * @author kehrer,mrindt
 */
public class RuleExecutionChecker {

	private Rule rule;
	protected Map<Node, Map<EReference, Set<Edge>>> src2OutType2OutEdge;

	/**
	 * Constructor
	 * @param rule
	 */
	public RuleExecutionChecker(Rule rule) {
		super();
		this.rule = rule;
	}

	/**
	 * Check if the rule to be checked is executable.
	 * It is executable if the correct number of forbid/require edges are
	 * set with respect to multiplicity invariants.
	 * 
	 * @return true or false
	 */
	public boolean isExecutable() {
		// Build index (of element type and reference type occurrences in rule)
		buildSrc2OutIndex();
		
		// Take a look at the number of occurring edge for each reference type
		// and find out if multiplicity invariants are violated
		for (Node src : src2OutType2OutEdge.keySet()) {
			Map<EReference, Set<Edge>> outType2Edge = src2OutType2OutEdge.get(src);
			for (EReference outType : outType2Edge.keySet()) {
				
				int sum_toBeMatched = 0; //delete/require/preserve edge occurrences
				int sum_forbid = 0;
				boolean hasDeletionEdge = false;

				//count
				for (Edge outEdge : outType2Edge.get(outType)) {
					if (HenshinRuleAnalysisUtilEx.isDeletionEdge(outEdge)
							|| HenshinRuleAnalysisUtilEx.isRequireEdge(outEdge)
							|| HenshinRuleAnalysisUtilEx.isPreservedEdge(outEdge)) {
						sum_toBeMatched++;
					}
					if (HenshinRuleAnalysisUtilEx.isForbiddenEdge(outEdge)) {
						sum_forbid++;
					}
					if (HenshinRuleAnalysisUtilEx.isDeletionEdge(outEdge)) {
						hasDeletionEdge = true;
					}
				}
				
				//***** Now decide executability *************************************/
				
				// not executable: if edge is upper bounded and rule does contain more 
				// delete/require/preserve edges of this type than allowed
				if (EReferenceInfo.isBounded(outType) && (sum_toBeMatched > outType.getUpperBound())) {
					return false;
				}
				// not executable: if edge is just lower bounded (but not upper bounded!) and there are
				// forbids in the rule which shouldn't be there.
				if (EReferenceInfo.isRequired(outType) && !EReferenceInfo.isBounded(outType) && (sum_forbid > 0)) {
					return false;
				}
				// not executable: if doubleBoundedAndMany and
				// there are too less/too many forbids of this type
				// (in case there is no deletion edge of this type)
				// or too less/too many delete/preserve/require edges of this type
				// (in case there is at least one deletion edge of this type)
				if (EReferenceInfo.isDoubleBoundedAndMany(outType) 
						&& ( !hasDeletionEdge && !(sum_forbid == outType.getUpperBound()) )
						&& (  hasDeletionEdge && (!(sum_toBeMatched == outType.getLowerBound())) )
					) {	
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * This method builds an index containing all occurring nodes in the rule and maps these
	 * to all it's outgoing edges. Also, it maps the used reference types to respective edge occurrences.
	 */
	private void buildSrc2OutIndex() {
		src2OutType2OutEdge = new HashMap<Node, Map<EReference, Set<Edge>>>();
		for (NodePair preservedNode : HenshinRuleAnalysisUtilEx.getPreservedNodes(rule)) {
			for (Edge edge : preservedNode.getLhsNode().getOutgoing()) {
				addEdge(preservedNode.getLhsNode(), edge);
			}
			for (Edge edge : preservedNode.getRhsNode().getOutgoing()) {
				addEdge(preservedNode.getLhsNode(), edge);
			}
			// include edges from nested conditions
			for (NestedCondition nc : rule.getLhs().getNestedConditions()) {
				ApplicationCondition condition = new ApplicationCondition(nc);
				Node acBoundaryNode = condition.getAcBoundaryNode(preservedNode.getLhsNode());
				if (acBoundaryNode != null){
					for (Edge edge : acBoundaryNode.getOutgoing()) {
						addEdge(preservedNode.getLhsNode(), edge);
					}
				}
			}			
		}
		for (Node deletionNode : HenshinRuleAnalysisUtilEx.getLHSMinusRHSNodes(rule)) {
			for (Edge edge : deletionNode.getOutgoing()) {
				addEdge(deletionNode, edge);
			}
		}
		for (Node creationNode : HenshinRuleAnalysisUtilEx.getRHSMinusLHSNodes(rule)) {
			for (Edge edge : creationNode.getOutgoing()) {
				addEdge(creationNode, edge);
			}
		}
	}

	private void addEdge(Node src, Edge edge) {
		if (src2OutType2OutEdge.get(src) == null) {
			src2OutType2OutEdge.put(src, new HashMap<EReference, Set<Edge>>());
		}
		if (src2OutType2OutEdge.get(src).get(edge.getType()) == null) {
			src2OutType2OutEdge.get(src).put(edge.getType(), new HashSet<Edge>());
		}

		src2OutType2OutEdge.get(src).get(edge.getType()).add(edge);
	}
}

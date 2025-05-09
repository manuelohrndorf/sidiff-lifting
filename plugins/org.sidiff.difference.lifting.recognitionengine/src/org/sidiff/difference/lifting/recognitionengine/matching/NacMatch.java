package org.sidiff.difference.lifting.recognitionengine.matching;

import java.util.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.interpreter.*;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.model.*;
import org.sidiff.common.collections.Pair;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Link;
import org.sidiff.common.henshin.*;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.lifting.recognitionengine.impl.RecognitionEngine;
import org.sidiff.editrule.analysis.conditions.EditRuleConditions;

public class NacMatch {

	private ApplicationCondition nac;
	private EngineBasedEditRuleMatch editRuleMatch;
	private RecognitionEngine recognitionEngine;

	// FIXME(TK): Can be multiple EObjects in case of Amalgamation!
	// lhsContextNode -> EObject in Difference (genau gesagt in Model A)
	private Map<Node, EObject> lhsContextOccurrencesA;

	// FIXME(TK): Can be multiple occurrences (1. wegen Amalgamation, 2. wegen
	// multipler invertierter NAC Matches)
	private Map<Node, EObject> forbidNodeOccurrencesA;

	private Map<Edge, Set<Link>> forbidEdgeOccurrencesA;

	public NacMatch(ApplicationCondition nac, EngineBasedEditRuleMatch editRuleMatch, RecognitionEngine recognitionEngine) {
		this.nac = nac;
		this.editRuleMatch = editRuleMatch;
		this.recognitionEngine = recognitionEngine;

		this.forbidNodeOccurrencesA = new HashMap<>();
		this.forbidEdgeOccurrencesA = new HashMap<>();

		// (1) try to find NAC context in model A
		findNacContextInA();

		if (isNacContextInAComplete()) {
			// (2) match inverted NAC

			// match nodeOccurrences
			createAndApplySearchRule();

			// now derive edge occurrences
			deriveEdgeOccurrences();
		}
	}

	/**
	 * Returns the occurrences of a forbid node in model A.
	 *
	 * @param forbidNode
	 * @return
	 */
	public Set<EObject> getForbidNodeOccurenceA(Node forbidNode) {
		EObject occurence = forbidNodeOccurrencesA.get(forbidNode);
		return occurence == null ? Collections.emptySet() : Collections.singleton(occurence);
	}

	/**
	 * Returns the occurrences of a forbid edge in model A.
	 *
	 * @param forbidNode
	 * @return
	 */
	public Set<Link> getForbidEdgeOccurenceA(Edge forbidEdge) {
		Set<Link> occurence = forbidEdgeOccurrencesA.get(forbidEdge);
		return occurence == null ? Collections.emptySet() : Collections.unmodifiableSet(occurence);
	}

	/**
	 *
	 * @return If this NacOccurrence indeed has at least one match in Model A
	 */
	public boolean hasMatch() {
		return !forbidNodeOccurrencesA.keySet().isEmpty() || !forbidEdgeOccurrencesA.keySet().isEmpty();
	}

	private boolean isNacContextInAComplete() {
		for (Node lhsContextNode : lhsContextOccurrencesA.keySet()) {
			if (!lhsContextOccurrencesA.containsKey(lhsContextNode)) {
				return false;
			}
		}
		return true;
	}

	private void findNacContextInA() {
		lhsContextOccurrencesA = new HashMap<>();
		for (Node lhsContextNode : nac.getLhsBoundaryNodes()) {
			Set<EObject> occurence = editRuleMatch.getOccurenceA(lhsContextNode);
			if (!occurence.isEmpty()) {
				// FIXME must be better than assertion
				assert occurence.size() == 1 :
					"Multiple Occurrences of a NAC-Node (Possible in case of Amalgamation) not yet supported!";
				lhsContextOccurrencesA.put(lhsContextNode, occurence.iterator().next());
			}
		}
	}

	private void createAndApplySearchRule() {

		// Copy NAC graph
		Copier lhsCopier = new Copier();
		Graph lhsSearchGraph = (Graph) lhsCopier.copy(nac.getNestedCondition().getConclusion());
		lhsCopier.copyReferences();

		// We don't want to copy attributes of boundary nodes
		for (Node acOriginalBoundaryNode : nac.getAcBoundaryNodes()) {
			Node acCopyBoundaryNode = (Node) lhsCopier.get(acOriginalBoundaryNode);
			acCopyBoundaryNode.getAttributes().clear();
		}

		// Create inverseMap for later access
		Map<EObject, EObject> inverseMap = new HashMap<>();
		for (EObject original : lhsCopier.keySet()) {
			inverseMap.put(lhsCopier.get(original), original);
		}

		// rhs Copier (because we want to create preserved nodes in searchRule)
		Copier rhsCopier = new Copier();
		Graph rhsSearchGraph = (Graph) rhsCopier.copy(lhsSearchGraph);
		rhsCopier.copyReferences();

		// create a search Rule (of which the LHS will be isomorph to the inverted NAC)
		Rule searchRule = HenshinFactory.eINSTANCE.createRule();
		searchRule.setName("searchNacOccurrenceInA_" + "_" + editRuleMatch.getEditRule().getExecuteModule().getName());
		searchRule.setActivated(true);
		searchRule.setLhs(lhsSearchGraph); // LHS
		searchRule.setRhs(rhsSearchGraph); // RHS

		// LHS - RHS Mappings:
		for (Node lhsNode : lhsSearchGraph.getNodes()) {
			Node rhsNode = (Node) rhsCopier.get(lhsNode);
			Mapping m = HenshinFactory.eINSTANCE.createMapping(lhsNode, rhsNode);
			searchRule.getMappings().add(m);
		}
		// Params
		for (String paramName : getRequiredRuleParameters()) {
			Parameter param = HenshinFactory.eINSTANCE.createParameter();
			param.setName(paramName);
			searchRule.getParameters().add(param);
		}

//		// Serialize transient search Rule (only for DEBUGGING)
//		HenshinUtil.serializeTempRule(searchRule);

		// Create Nac context in A as prematch
		Match preMatch = new MatchImpl(searchRule);
		for (Node lhsNode : lhsContextOccurrencesA.keySet()) {
			Node nacNode = nac.getAcBoundaryNode(lhsNode); // the original
			Node nacNodeCopy = (Node) lhsCopier.get(nacNode); // the copy
			EObject occurrence = lhsContextOccurrencesA.get(lhsNode);

			assert nacNodeCopy != null && occurrence != null;
			preMatch.setNodeTarget(nacNodeCopy, occurrence);
		}

		// Set value parameters (if any)
		for (Parameter p : searchRule.getParameters()) {
			Object pValue = editRuleMatch.getRecognitionRuleMatch().getParameterValue(p.getName());
			if (pValue != null) {
				preMatch.setParameterValue(searchRule.getParameter(p.getName()), pValue);
			}
		}

		// Try to find matches
		Engine emfEngine = new SelfCleaningEngineImpl();
		List<Match> matches = new ArrayList<>();
		matches.addAll(getPostconditionMatches(emfEngine, searchRule, preMatch));
		matches.addAll(getPreconditionMatches(emfEngine, searchRule, preMatch));

		// Now we can capture the forbidNode occurrences
		if (!matches.isEmpty()) {
			// FIXME: Multiple matches unterstützen!
			// assert (matches.size() == 1) :
			// "Multiple matches of inverted NAC pattern in model A not yet supported!";

			Match match = matches.get(0);
			for (Node copiedNode : searchRule.getLhs().getNodes()) {
				Node originalNode = (Node) inverseMap.get(copiedNode); // nacNode

				// if node is a forbid node (i.e is no boundary node), save it
				if (nac.getLhsBoundaryNode(originalNode) == null) {
					LogUtil.log(LogEvent.DEBUG,
							" NAC node trace A: " + originalNode + " ==> " + match.getNodeTarget(copiedNode));
					forbidNodeOccurrencesA.put(originalNode, match.getNodeTarget(copiedNode));
				}
			}
		}
		emfEngine.shutdown();
	}

	private List<Match> getPreconditionMatches(Engine emfEngine, Rule searchRule, Match preMatch) {

		if (EditRuleConditions.isPrecondition(nac.getNestedCondition().getConclusion())) {
			List<Match> matches = new ArrayList<>();
			EGraph graph = recognitionEngine.getGraphFactory().getModelAGraph();

			for (Match m : emfEngine.findMatches(searchRule, graph, preMatch)) {
				matches.add(m);
			}

			return matches;
		}

		return Collections.emptyList();
	}

	private List<Match> getPostconditionMatches(Engine emfEngine, Rule searchRule, Match preMatch) {
		if (EditRuleConditions.isPostcondition(nac.getNestedCondition().getConclusion())) {
			List<Match> matches = new ArrayList<>();
			EGraph graph = recognitionEngine.getGraphFactory().getModelBGraph();
			emfEngine.findMatches(searchRule, graph, preMatch).forEach(matches::add);
			return matches;
		}
		return Collections.emptyList();
	}

	private void deriveEdgeOccurrences() {
		for (Edge edge : getAllNacEdges()) {
			Set<Link> edgeOccurrences = new HashSet<>();

			// 1. Kreuzprodukt aus getOccurenceA(src) und getOccurenceA(tgt)
			// bilden
			Set<EObject> srcOccurrenceA = getOccurrenceA(edge.getSource());
			Set<EObject> tgtOccurrenceA = getOccurrenceA(edge.getTarget());
			Set<Pair<EObject,EObject>> crossProduct = MatchingHelper.getCartesianProduct(srcOccurrenceA, tgtOccurrenceA);

			// 2. Checken, welche References (= edge occurrence) tatsächlich
			// existieren
			for (Pair<EObject,EObject> tuple : crossProduct) {
				if (EMFModelAccess.getNodeNeighbors(tuple.getFirst(), edge.getType()).contains(tuple.getSecond())) {
					// edge occurrence found
					edgeOccurrences.add(new Link(tuple.getFirst(), tuple.getSecond(), edge.getType()));
				}
			}

			if (!edgeOccurrences.isEmpty()) {
				LogUtil.log(LogEvent.DEBUG, " NAC edge trace A: " + edge + " (" + edge.getType().getName() + ") ==> "
						+ edgeOccurrences);

				forbidEdgeOccurrencesA.put(edge, edgeOccurrences);
			}
		}
	}

	/**
	 * Derive all Nac-Edges from the nodeOccurrencesA. All edges are part of a
	 * NAC of the edit rule.
	 *
	 * @return The set of NAC-edges
	 */
	private List<Edge> getAllNacEdges() {
		return nac.getNestedCondition().getConclusion().getEdges();
	}

	/**
	 * Konvention: Wenn nur in NAC, dann NAC-node. Ansonsten LHS-Node der Regel.
	 *
	 * @param nacNode
	 * @return
	 */
	private Set<EObject> getOccurrenceA(Node nacNode) {
		if (nac.getLhsBoundaryNode(nacNode) == null) {
			// forbid node
			return getForbidNodeOccurenceA(nacNode);
		}
		// LHS node of the rule
		Node lhsNode = nac.getLhsBoundaryNode(nacNode);
		EObject occurence = lhsContextOccurrencesA.get(lhsNode);
		return occurence == null ? Collections.emptySet() : Collections.singleton(occurence);
	}

	/**
	 * Als Parameter der invertierten NAC Rule werden nur diejenigen Parameter
	 * benötigt, die von der NAC auch verwendet werden. OBJECT-Parameter machen
	 * eigentlich keinen Sinn in der NAC Rule, daher suchen wir nach VALUE
	 * Parametern.
	 *
	 * @return
	 */
	private Set<String> getRequiredRuleParameters() {
		// get all attribute values
		Set<String> attrValues = new HashSet<>();
		for (Node node : nac.getNonBoundaryNodes()) {
			for (Attribute attr : node.getAttributes()) {
				attrValues.add(attr.getValue());
			}
		}

		Set<String> res = new HashSet<>();
		for (Rule rule : HenshinModuleAnalysis.getAllRules(editRuleMatch.getEditRule().getExecuteModule())) {
			for (Parameter param : rule.getParameters()) {
				if (attrValues.contains(param.getName())) {
					res.add(param.getName());
				}
			}
		}

		return res;
	}
}

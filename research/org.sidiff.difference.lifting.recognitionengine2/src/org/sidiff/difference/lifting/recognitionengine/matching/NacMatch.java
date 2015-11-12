package org.sidiff.difference.lifting.recognitionengine.matching;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.Link;
import org.sidiff.common.henshin.ApplicationCondition;
import org.sidiff.common.henshin.HenshinModuleAnalysis;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngine;
import org.sidiff.editrule.analysis.EditRuleConditions;

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
		super();
		this.nac = nac;
		this.editRuleMatch = editRuleMatch;
		this.recognitionEngine = recognitionEngine;

		this.forbidNodeOccurrencesA = new HashMap<Node, EObject>();
		this.forbidEdgeOccurrencesA = new HashMap<Edge, Set<Link>>();

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
		if (forbidNodeOccurrencesA.get(forbidNode) == null) {
			return new HashSet<EObject>();
		} else {
			// FIXME: Simply return forbidNodeOccurrencesA.get(forbidNode)
			HashSet<EObject> res = new HashSet<EObject>();
			res.add(forbidNodeOccurrencesA.get(forbidNode));
			return res;
		}
	}

	/**
	 * Returns the occurrences of a forbid edge in model A.
	 * 
	 * @param forbidNode
	 * @return
	 */
	public Set<Link> getForbidEdgeOccurenceA(Edge forbidEdge) {
		if (forbidEdgeOccurrencesA.get(forbidEdge) == null) {
			return new HashSet<Link>();
		} else {
			return forbidEdgeOccurrencesA.get(forbidEdge);
		}
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
			if (lhsContextOccurrencesA.get(lhsContextNode) == null) {
				return false;
			}
		}

		return true;
	}

	private void findNacContextInA() {
		lhsContextOccurrencesA = new HashMap<Node, EObject>();

		for (Node lhsContextNode : nac.getLhsBoundaryNodes()) {
			EObject occurrence = null;
			if (!editRuleMatch.getOccurenceA(lhsContextNode).isEmpty()) {
				// FIXME must be better than assertion
				assert (editRuleMatch.getOccurenceA(lhsContextNode).size() == 1) : "Multiple Occurrences of a NAC-Node (Possible in case of Amalgamation) not yet supported!";
				occurrence = editRuleMatch.getOccurenceA(lhsContextNode).iterator().next();
			}
			lhsContextOccurrencesA.put(lhsContextNode, occurrence);
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
			for (Iterator<Attribute> iterator = acCopyBoundaryNode.getAttributes().iterator(); iterator.hasNext();) {
				iterator.next();
				iterator.remove();				
			}
		}
		
		// Create inverseMap for later access
		Map<EObject, EObject> inverseMap = new HashMap<EObject, EObject>();
		for (EObject original : lhsCopier.keySet()) {
			EObject copy = lhsCopier.get(original);
			inverseMap.put(copy, original);
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

			assert (nacNodeCopy != null && occurrence != null);
			preMatch.setNodeTarget(nacNodeCopy, occurrence);
		}

		// Set value parameters (if any)
		for (Parameter p : searchRule.getParameters()) {
			Object pValue = editRuleMatch.getRecognitionRuleMatch().getParameterValue(p.getName());
			if (pValue != null){
				preMatch.setParameterValue(searchRule.getParameter(p.getName()), pValue);
			}
		}
		
		// Try to find matches
		Engine emfEngine = new EngineImpl();
		List<Match> matches = new ArrayList<Match>();
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
	}
	
	private List<Match> getPreconditionMatches(Engine emfEngine, Rule searchRule, Match preMatch) {
		// TODO: TEST
		if (EditRuleConditions.isPrecondition(nac.getNestedCondition().getConclusion())) {
			List<Match> matches = new ArrayList<Match>();		 
			EGraph graph = recognitionEngine.getGraphFactory().getModelAGraph();
			
			for (Match m : emfEngine.findMatches(searchRule, graph, preMatch)) {
				matches.add(m);
			}
			
			return matches;
		}
		
		return Collections.emptyList();
	}
	
	private List<Match> getPostconditionMatches(Engine emfEngine, Rule searchRule, Match preMatch) {
		// TODO: TEST
		if (EditRuleConditions.isPostcondition(nac.getNestedCondition().getConclusion())) {
			List<Match> matches = new ArrayList<Match>();		 
			EGraph graph = recognitionEngine.getGraphFactory().getModelBGraph();
			
			for (Match m : emfEngine.findMatches(searchRule, graph, preMatch)) {
				matches.add(m);
			}
			
			return matches;
		}
		
		return Collections.emptyList();
	}

	private void deriveEdgeOccurrences() {
		for (Edge edge : getAllNacEdges()) {
			Set<Link> edgeOccurrences = new HashSet<Link>();

			// 1. Kreuzprodukt aus getOccurenceA(src) und getOccurenceA(tgt)
			// bilden
			Set<EObject> srcOccurrenceA = getOccurrenceA(edge.getSource());
			Set<EObject> tgtOccurrenceA = getOccurrenceA(edge.getTarget());

			Set<EObject[]> crossProduct = MatchingHelper.getCartesianProduct(srcOccurrenceA, tgtOccurrenceA);

			// 2. Checken, welche References (= edge occurrence) tatsächlich
			// existieren
			for (EObject[] tuple : crossProduct) {
				if (EMFModelAccess.getNodeNeighbors(tuple[0], edge.getType()).contains(tuple[1])) {
					// edge occurrence found
					edgeOccurrences.add(new Link(tuple[0], tuple[1], edge.getType()));
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
		} else {
			// LHS node of the rule
			Node lhsNode = nac.getLhsBoundaryNode(nacNode);

			// FIXME: Simply return lhsContextOccurrencesA.get(lhsNode)
			Set<EObject> res = new HashSet<EObject>();
			assert (lhsContextOccurrencesA.get(lhsNode) != null);
			res.add(lhsContextOccurrencesA.get(lhsNode));
			return res;
		}
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
		Set<String> attrValues = new HashSet<String>();
		for (Node node : nac.getNonBoundaryNodes()) {
			for (Attribute attr : node.getAttributes()) {
				attrValues.add(attr.getValue());
			}
		}

		Set<String> res = new HashSet<String>();
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

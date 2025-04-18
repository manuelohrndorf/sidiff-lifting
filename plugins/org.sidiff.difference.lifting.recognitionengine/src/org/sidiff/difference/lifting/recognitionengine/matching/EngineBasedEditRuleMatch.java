package org.sidiff.difference.lifting.recognitionengine.matching;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.sidiff.common.emf.access.Link;
import org.sidiff.common.henshin.ApplicationCondition;
import org.sidiff.common.henshin.HenshinRuleAnalysisUtilEx;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.lifting.recognitionengine.impl.RecognitionEngine;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.difference.rulebase.Trace;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;

/**
 * A specific subclass of {@link BasicEditRuleMatch} that creates an
 * EditRuleBatch based on a given {@link RecognitionRuleMatch}.
 * 
 * Additionally, this variant of an EditRuleMatch introduces NacMatches.
 * 
 * @author kehrer
 */
public class EngineBasedEditRuleMatch extends BasicEditRuleMatch {

	/**
	 * The {@link RecognitionRuleMatch from which this EditRuleMatch has been
	 * created.
	 */
	private RecognitionRuleMatch recognitionRuleMatch;
	
	/**
	 * NAC occurrences in model A.
	 */
	private Set<NacMatch> nacOccurrences;

	public EngineBasedEditRuleMatch(RecognitionRuleMatch recognitionRuleMatch, RecognitionEngine recognitionEngine) {
		this.recognitionRuleMatch = recognitionRuleMatch;

		StringBuilder info = new StringBuilder();

		// First: Process all nodes of RecognitionRule to get Node-Occurrences
		recognitionRuleMatch.getNodeMapping().entrySet().forEach(nodeMapping -> {
			Node rrNode = nodeMapping.getKey();
			Set<EObject> diffObjects = nodeMapping.getValue();

			Node erNodeA = getEditRuleNodeViaTraceA(rrNode, recognitionEngine.getSetup().getRulebases());
			if (erNodeA != null) {
				erNodeA = getKeyNode(erNodeA);

				info.append(" node trace A: " + erNodeA + " ==> ");
				info.append(rrNode + " ==> ");
				info.append(diffObjects);

				nodeOccurencesA.put(erNodeA, diffObjects);
			}
			Node erNodeB = getEditRuleNodeViaTraceB(rrNode, recognitionEngine.getSetup().getRulebases());			
			if (erNodeB != null) {
				erNodeB = getKeyNode(erNodeB);

				info.append("\n node trace B: " + erNodeB +  " (" + erNodeB.eResource() +  ") ==> ");
				info.append(rrNode + " ==> ");
				info.append(diffObjects);

				nodeOccurencesB.put(erNodeB, diffObjects);
			}
		});

		// Secondly: In the RecognitionRule, we may potentially not search in A
		// and B (in case of preserve nodes)
		// ==> Thus, we do a lookup using the correspondences to maybe get
		// corresponding partners
		for (Node erNode : nodeOccurencesA.keySet()) {
			if (HenshinRuleAnalysisUtilEx.isPreservedNode(erNode) && !nodeOccurencesB.keySet().contains(erNode)) {
				Set<EObject> diffObjectsB = new HashSet<>();
				for (EObject diffObjectA : getOccurenceA(erNode)) {
					EObject diffObjectB = recognitionEngine.getSetup().getDifference().getCorrespondingObjectInB(diffObjectA);
					if (diffObjectB != null) {
						diffObjectsB.add(diffObjectB);
					}
				}
				if (!diffObjectsB.isEmpty()) {
					info.append("\n node trace B (complement): " + erNode + " ==> ");
					info.append(diffObjectsB);

					nodeOccurencesB.put(erNode, diffObjectsB);
				}
			}
		}
		for (Node erNode : nodeOccurencesB.keySet()) {
			if (HenshinRuleAnalysisUtilEx.isPreservedNode(erNode) && !nodeOccurencesA.keySet().contains(erNode)) {
				Set<EObject> diffObjectsA = new HashSet<>();
				for (EObject diffObjectB : getOccurenceB(erNode)) {
					EObject diffObjectA = recognitionEngine.getSetup().getDifference().getCorrespondingObjectInA(diffObjectB);
					if (diffObjectA != null) {
						diffObjectsA.add(diffObjectA);
					}
				}
				if (!diffObjectsA.isEmpty()) {
					info.append("\n node trace A (complement): " + erNode + " ==> ");
					info.append(diffObjectsA);

					nodeOccurencesA.put(erNode, diffObjectsA);
				}
			}
		}

		// Finally: Derive Edge-Occurrences from source- and
		// target-Object-Occurrences
		String edgeInfo = super.deriveEdgeOccurrences();
		info.append(edgeInfo);

		LogUtil.log(LogEvent.DEBUG, "== EditRule-Match: " + getEditRule().getExecuteModule().getName());
		LogUtil.log(LogEvent.DEBUG, info.toString());

		// And now let's search for NAC occurrences, which is delegated to Nac
		// and NacOccurrence

		// FIXME: May be many NACs
		// (CP 2020-06-23): At the moment we support multiple NACs if they are contained in an AND Formula.
		// Furthermore the NAC must have a NestedCondition as child.
//		Collection<ApplicationCondition> nac = MatchingHelper.getNAC(getEditRule());
//		if (nac != null) {
//			NacMatch no = new NacMatch(nac, this, recognitionEngine);
//			if (no.hasMatch()) {
//				nacOccurrences.add(no);
//			}
//		}
		nacOccurrences = new HashSet<>();
		for(ApplicationCondition nac : MatchingHelper.getNAC(getEditRule())) {
			NacMatch no = new NacMatch(nac, this, recognitionEngine);
			if(no.hasMatch()) {
				nacOccurrences.add(no);
			}
		}
	}

	/**
	 * 
	 * @return The Nac-Occurrences in Model A if presented
	 */
	@Override
	public Set<NacMatch> getNacOccurrences() {
		return Collections.unmodifiableSet(nacOccurrences);
	}

	/**
	 * Convenience function for client access:<br>
	 * Returns the occurrences of a forbid node in model A.<br>
	 * In case of multiple occurrences of the NAC to which the given forbidNode
	 * belongs to, the union of all forbidNode-occurrences (via different NACs)
	 * is returned.
	 * 
	 * @param forbidNode
	 * @return
	 */
	@Override
	public Set<EObject> getForbidNodeOccurenceA(Node forbidNode) {
		Set<EObject> res = new HashSet<>();
		for (NacMatch nacOccurrence : getNacOccurrences()) {
			res.addAll(nacOccurrence.getForbidNodeOccurenceA(forbidNode));
		}
		return res;
	}

	/**
	 * Convenience function for client access:<br>
	 * Returns the occurrences of a forbid edge in model A.<br>
	 * In case of multiple occurrences of the NAC to which the given forbidEdge
	 * belongs to, the union of all forbidEdge-occurrences (via different NACs)
	 * is returned.
	 * 
	 * @param forbidEdge
	 * @return
	 */
	@Override
	public Set<Link> getForbidEdgeOccurenceA(Edge forbidEdge) {
		Set<Link> res = new HashSet<>();
		for (NacMatch nacOccurrence : getNacOccurrences()) {
			res.addAll(nacOccurrence.getForbidEdgeOccurenceA(forbidEdge));
		}
		return res;
	}

	@Override
	public RecognitionRuleMatch getRecognitionRuleMatch() {
		return recognitionRuleMatch;
	}

	/**
	 * Gets the corresponding editRule node for recognitionRuleNode (via trace
	 * A) and lazy sets the EditRule to which the editRule node belongs to.
	 * 
	 * @param recognitionRuleNode
	 * @param usedRulebases
	 * @return
	 */
	private Node getEditRuleNodeViaTraceA(Node recognitionRuleNode, Set<ILiftingRuleBase> usedRulebases) {
		for (ILiftingRuleBase iRuleBase : usedRulebases) {
			Trace trace = iRuleBase.getTraceA(recognitionRuleNode);
			if (trace != null) {
				setEditRule(((RecognitionRule) trace.eContainer()).getEditRule());
				return trace.getEditRuleTrace();
			}
		}
		return null;
	}

	/**
	 * Gets the corresponding editRule node for recognitionRuleNode (via trace
	 * B) and lazy sets the EditRule to which the editRule node belongs to.
	 * 
	 * @param recognitionRuleNode
	 * @param usedRulebases
	 * @return
	 */
	private Node getEditRuleNodeViaTraceB(Node recognitionRuleNode, Set<ILiftingRuleBase> usedRulebases) {
		for (ILiftingRuleBase iRuleBase : usedRulebases) {
			Trace trace = iRuleBase.getTraceB(recognitionRuleNode);
			if (trace != null) {
				setEditRule(((RecognitionRule) trace.eContainer()).getEditRule());
				return trace.getEditRuleTrace();
			}
		}
		return null;
	}

	/**
	 * Checks if the edit rule is matched non-injectively. <br>
	 * The reason for this check is that this cannot be checked based on the recognition rule
	 * (done by Henshin) in all cases. 
	 * 
	 * @return true if the edit rule match is non-injective
	 */
	public boolean isNonInjective() {
		// Check A traces (all erNodes must be traced to distinct model elements in A)
		for (Node erNode : getMatchedNodesA()) {
			Set<EObject> occurences = getOccurenceA(erNode);
			for (Node otherErNode : getMatchedNodesA()) {
				if (otherErNode != erNode){
					Set<EObject> otherOccurences = getOccurenceA(otherErNode);
					// Intersection test
					Set<EObject> intersection = new HashSet<>(occurences);
					intersection.retainAll(otherOccurences);
					if (!intersection.isEmpty()) {
						return true;
					}
				}
			}
		}

		// Check B traces (all erNodes must be traced to distinct model elements in B)
		for (Node erNode : getMatchedNodesB()) {
			Set<EObject> occurences = getOccurenceB(erNode);
			for (Node otherErNode : getMatchedNodesB()) {
				if (otherErNode != erNode){
					Set<EObject> otherOccurences = getOccurenceB(otherErNode);
					// Intersection test
					Set<EObject> intersection = new HashSet<>(occurences);
					intersection.retainAll(otherOccurences);
					if (!intersection.isEmpty()) {
						return true;
					}
				}
			}
		}

		return false;
	}
}

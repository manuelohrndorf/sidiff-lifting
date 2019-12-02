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

		StringBuffer info = new StringBuffer();

		// First: Process all nodes of RecognitionRule to get Node-Occurrences
		recognitionRuleMatch.getNodeMapping().entrySet().forEach(nodeMapping -> {
			Node rrNode = nodeMapping.getKey();
			Set<EObject> diffObjects = nodeMapping.getValue();

			Node erNode = getEditRuleNodeViaTraceA(rrNode, recognitionEngine.getSetup().getRulebases());
			if (erNode != null) {
				erNode = getKeyNode(erNode);

				info.append(" node trace A: " + erNode + " ==> ");
				info.append(rrNode + " ==> ");
				info.append(diffObjects);

				nodeOccurencesA.put(erNode, diffObjects);
			}
			erNode = getEditRuleNodeViaTraceB(rrNode, recognitionEngine.getSetup().getRulebases());			
			if (erNode != null) {
				erNode = getKeyNode(erNode);

				info.append("\n node trace B: " + erNode +  " (" + erNode.eResource() +  ") ==> ");
				info.append(rrNode + " ==> ");
				info.append(diffObjects);

				nodeOccurencesB.put(erNode, diffObjects);
			}
		});

		// Secondly: In the RecognitionRule, we may potentially not search in A
		// and B (in case of preserve nodes)
		// ==> Thus, we do a lookup using the correspondences to maybe get
		// corresponding partners
		for (Node erNode : nodeOccurencesA.keySet()) {
			if (HenshinRuleAnalysisUtilEx.isPreservedNode(erNode) && !nodeOccurencesB.keySet().contains(erNode)) {
				Set<EObject> diffObjectsB = new HashSet<EObject>();
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
				Set<EObject> diffObjectsA = new HashSet<EObject>();
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
		nacOccurrences = new HashSet<NacMatch>();
		// FIXME: May be many NACs
		ApplicationCondition nac = MatchingHelper.getNAC(getEditRule());
		if (nac != null) {
			NacMatch no = new NacMatch(nac, this, recognitionEngine);
			if (no.hasMatch()) {
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

}

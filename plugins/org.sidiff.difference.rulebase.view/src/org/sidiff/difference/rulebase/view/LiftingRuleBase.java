package org.sidiff.difference.rulebase.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.difference.rulebase.Trace;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.view.editrule.EditRuleBase;

/**
 * Basic implementation of @link {@link ILiftingRuleBase}.
 */
public class LiftingRuleBase extends EditRuleBase implements ILiftingRuleBase {
	
	/**
	 * Trace index.
	 */
	private Map<Node, Trace> tracesA;
	
	/**
	 * Trace index.
	 */
	private Map<Node, Trace> tracesB;
	
	/**
	 * Cache: All rulebase recognition rule items. 
	 */
	private List<RecognitionRule> recognitionRules;
	
	/**
	 * Cache: Active (henshin) recognition rule patterns.
	 */
	private Set<Rule> activeUnits;
	
	@Override
	public List<RecognitionRule> getRecognitionRules() {
		
		if (recognitionRules == null) {
			recognitionRules = getRuleBase().getEditRuleAttachments(RecognitionRule.class);
		}
		
		return recognitionRules;
	}
	
	@Override
	public Set<Rule> getActiveRecognitonUnits() {
		if (activeUnits == null) {
			activeUnits = new HashSet<Rule>();
			for (EditRule eo : getActiveEditRules()) {
				activeUnits.add(eo.getEditRuleAttachment(RecognitionRule.class).getRecognitionMainUnit());
			}
		}
		return activeUnits;
	}

	@Override
	public Rule getRecognitionUnit(String name) {
		for (Rule unit : getActiveRecognitonUnits()) {
			if (getRuleName(unit).equals(name)) {
				return unit;
			}
		}
		return null;
	}

	@Override
	public Rule getRecognitionUnit(Unit editRule) {
		for (RecognitionRule rr : getRecognitionRules()) {
			if (rr.getEditRule().getExecuteMainUnit() == editRule) {
				return rr.getRecognitionMainUnit();
			}
		}
		return null;
	}

	@Override
	public Unit getEditUnit(Rule recognitionRule) {
		for (RecognitionRule rule : getRecognitionRules()) {
			if (rule.getRecognitionMainUnit().equals(recognitionRule)) {
				return rule.getEditRule().getExecuteMainUnit();
			}
		}
		return null;
	}

	@Override
	public Trace getTraceA(Node recognitionRuleNode) {
		Trace trace = getTraceAIndex().get(recognitionRuleNode);

		// Check for missing or inconsistent edit rule:
		if (trace != null) {
			Node editRuleNode = trace.getEditRuleTrace();

			if (editRuleNode.eIsProxy()) {
				String uri = ((InternalEObject) editRuleNode).eProxyURI().toString();
				throw new RuntimeException("Corrupted edit to recognition rule trace!" + "\n"
						+ "Edit rule node not found: " + uri);
			}
		}

		return trace;
	}

	@Override
	public Trace getTraceB(Node recognitionRuleNode) {
		Trace trace = getTraceBIndex().get(recognitionRuleNode);

		// Check for missing or inconsistent edit rule:
		if (trace != null) {
			Node editRuleNode = trace.getEditRuleTrace();

			if (editRuleNode.eIsProxy()) {
				String uri = ((InternalEObject) editRuleNode).eProxyURI().toString();
				throw new RuntimeException("Corrupted edit to recognition rule trace!" + "\n"
						+ "Edit rule node not found: " + uri);
			}
		}

		return trace;
	}
	
	private String getRuleName(Unit unit) {	
		Module module = unit.getModule();
		return module.getName();
	}
	
	/**
	 * Lazy builds an index for all model A traces.
	 * 
	 * @return A {@link HashMap} from the recognition rule nodes to the
	 *         corresponding {@link Trace}.
	 */
	private Map<Node, Trace> getTraceAIndex() {
		if (tracesA == null) {
			tracesA = new HashMap<Node, Trace>();

			for (RecognitionRule rr : getRecognitionRules()) {
				for (Trace trace : rr.getTracesA()) {
					tracesA.put(trace.getRecognitionRuleTrace(), trace);
				}
			}
		}

		return tracesA;
	}

	/**
	 * Lazy builds an index for all model B traces.
	 * 
	 * @return A {@link HashMap} from the recognition rule nodes to the
	 *         corresponding {@link Trace}.
	 */
	private Map<Node, Trace> getTraceBIndex() {
		if (tracesB == null) {
			tracesB = new HashMap<Node, Trace>();

			for (RecognitionRule rr : getRecognitionRules()) {
				for (Trace trace : rr.getTracesB()) {
					tracesB.put(trace.getRecognitionRuleTrace(), trace);
				}
			}
		}

		return tracesB;
	}
	
	@Override
	public void clearCaches() {
		tracesA = null;
		tracesB = null;
		recognitionRules = null;
		activeUnits = null;
	}
}

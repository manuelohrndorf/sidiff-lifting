package org.sidiff.difference.rulebase.extension;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.PotentialDependency;
import org.sidiff.difference.rulebase.RecognitionRule;
import org.sidiff.difference.rulebase.RuleBase;
import org.sidiff.difference.rulebase.RuleBaseItem;
import org.sidiff.difference.rulebase.Trace;
import org.sidiff.difference.rulebase.util.RuleBaseUtil;

public abstract class AbstractRuleBase implements IRuleBase {

	/**
	 * The underlying EMF rulebase object
	 */
	private RuleBase rulebase;

	private Map<Node, Trace> tracesA;
	private Map<Node, Trace> tracesB;

	/**
	 * Mapping: src EditRule -> set of PotentialDependencies where EditRule is the source of
	 */
	private Map<EditRule, Set<PotentialDependency>> potDepIndex;

	@Override
	public String getName() {
		return getRuleBase().getName();
	}

	@Override
	public String getCharacteristicDocumentType() {
		return getRuleBase().getCharacteristicDocumentType();
	}

	@Override
	public Set<String> getDocumentTypes() {
		Set<String> docTypes = new HashSet<String>(getRuleBase().getDocumentTypes());
		return Collections.unmodifiableSet(docTypes);
	}

	@Override
	public Set<RuleBaseItem> getActiveRuleBaseItems() {
		Set<RuleBaseItem> items = new HashSet<RuleBaseItem>();
		for (RuleBaseItem item : getRuleBase().getItems()) {
			if (item.isActive()) {
				items.add(item);
			}
		}
		return items;
	}

	@Override
	public Set<RuleBaseItem> getAllRuleBaseItems() {
		return new HashSet<RuleBaseItem>(getRuleBase().getItems());
	}

	@Override
	public Set<Rule> getActiveRecognitionTransformationUnits() {
		Set<Rule> units = RuleBaseUtil.getActiveRecognitonRules(getRuleBase());
		return units;
	}

	@Override
	public Rule getRecognitionTransformationUnit(String name) {
		Set<Rule> recognMainUnits = getActiveRecognitionTransformationUnits();
		for (Rule unit : recognMainUnits) {
			if (getRuleName(unit).equals(name)) {
				return unit;
			}
		}
		return null;
	}

	@Override
	public Rule getRecognitionTransformationUnit(Unit editRule) {
		for (EditRule rule : getRuleBase().getEditRules()) {
			if (rule.getExecuteMainUnit().equals(editRule)) {
				return rule.getRecognitionRule().getRecognitionMainUnit();
			}
		}
		return null;
	}

	@Override
	public Set<EditRule> getActiveEditRules() {
		Set<EditRule> res = new HashSet<EditRule>();
		for (RuleBaseItem item : getActiveRuleBaseItems()) {
			res.add(item.getEditRule());
		}

		return res;
	}

	@Override
	public EditRule getEditRule(String name) {
		Set<EditRule> editRules = getActiveEditRules();
		for (EditRule er : editRules) {
			if (getRuleName(er.getExecuteMainUnit()).equals(name)) {
				return er;
			}
		}
		return null;
	}

	@Override
	public Unit getEditRule(Rule recognitionRule) {
		for (RecognitionRule rule : getRuleBase().getRecognitionRules()) {
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

	@Override
	public Set<PotentialDependency> getPotentialDependencies(EditRule editRule) {
		Set<PotentialDependency> res = getPotentialDependencyIndex().get(editRule);
		if (res != null) {
			return res;
		} else {
			return Collections.emptySet();
		}
	}

	@Override
	public void activateAllRuleBaseItems() {
		for (RuleBaseItem item : getRuleBase().getItems()) {
			item.setActive(true);
		}
	}

	@Override
	public void deactivateAllRuleBaseItems() {
		for (RuleBaseItem item : getRuleBase().getItems()) {
			item.setActive(false);
		}
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

			for (RuleBaseItem item : getRuleBase().getItems()) {
				for (Trace trace : item.getTracesA()) {
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

			for (RuleBaseItem item : getRuleBase().getItems()) {
				for (Trace trace : item.getTracesB()) {
					tracesB.put(trace.getRecognitionRuleTrace(), trace);
				}
			}
		}

		return tracesB;
	}

	/**
	 * Lazy builds an index for all potential Dependencies
	 * 
	 * @return
	 */
	private Map<EditRule, Set<PotentialDependency>> getPotentialDependencyIndex() {
		if (potDepIndex == null) {
			potDepIndex = new HashMap<EditRule, Set<PotentialDependency>>();

			// Nodes
			for (PotentialDependency potDep : getRuleBase().getPotentialNodeDependencies()) {
				if (!potDepIndex.containsKey(potDep.getSourceRule())) {
					potDepIndex.put(potDep.getSourceRule(), new HashSet<PotentialDependency>());
				}
				potDepIndex.get(potDep.getSourceRule()).add(potDep);
			}

			// Edges
			for (PotentialDependency potDep : getRuleBase().getPotentialEdgeDependencies()) {
				if (!potDepIndex.containsKey(potDep.getSourceRule())) {
					potDepIndex.put(potDep.getSourceRule(), new HashSet<PotentialDependency>());
				}
				potDepIndex.get(potDep.getSourceRule()).add(potDep);
			}

			// Attributes
			for (PotentialDependency potDep : getRuleBase().getPotentialAttributeDependencies()) {
				if (!potDepIndex.containsKey(potDep.getSourceRule())) {
					potDepIndex.put(potDep.getSourceRule(), new HashSet<PotentialDependency>());
				}
				potDepIndex.get(potDep.getSourceRule()).add(potDep);
			}
		}

		return potDepIndex;
	}

	/**
	 * Loads the corresponding rule base if necessary.
	 * 
	 * @return The corresponding rule base.
	 */
	private RuleBase getRuleBase() {
		if (rulebase == null) {
			rulebase = RuleBaseUtil.loadRuleBase(getRuleBaseURI(), getRuleBasePluginID());
		}
		return rulebase;
	}

	private String getRuleName(Unit unit) {	
			Module module = unit.getModule();
				return module.getName();
	}

	/**
	 * Template-method that must be implemented by subclasses: Returns the URI String of the rulebase.
	 * 
	 * @return URI String
	 */
	protected abstract String getRuleBaseURI();
	
	/**
	 * emplate-method that must be implemented by subclasses: Returns the plug-in ID of the  rulebase.
	 * 
	 * @return The plug-in ID
	 */
	protected abstract String getRuleBasePluginID();
}

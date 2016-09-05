package org.sidiff.difference.lifting.recognitionengine.rules;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.difference.lifting.recognitionengine.graph.LiftingGraphDomainMap;

/**
 * Filters unmatchable recognition rules by counting the atomic changes per type
 * in the difference and in the recognition rule. That means a recognition rule
 * is matchable if there are at least the same count of changes in the
 * difference as in the rule.
 */
public class RecognitionRuleFilter {

	/**
	 * The corresponding change index knowing the amount of low-level changes per type.
	 */
	private LiftingGraphDomainMap liftingGraphDomainMap;
	
	/**
	 * The recognition rules with their corresponding change type blueprints.
	 */
	private Map<Rule, RecognitionRuleBlueprint> recognitionRules;

	/**
	 * Start recognition rule filter. Filters unmatchable recognition rules by
	 * counting the atomic changes in the difference and in the recognition rule.
	 * 
	 * @param analysis
	 *            the corresponding change index knowing the amount of low-level
	 *            changes per type.
	 * @param recognitionRules
	 *            all recognition rules to filter.
	 * @return the filtered unmatchable recognition rules.
	 */
	public Set<Rule> filter(LiftingGraphDomainMap differenceDomainMap, 
			Map<Rule, RecognitionRuleBlueprint> recognitionRules) {

		this.liftingGraphDomainMap = differenceDomainMap;
		this.recognitionRules = recognitionRules;

		// Filtered rules
		Set<Rule> filtered = new HashSet<Rule>();

		// Analyze recognition rules
		for (Rule rr : recognitionRules.keySet()) {
			if (!matchable(rr)) {
				filtered.add(rr);
			}
		}

		return filtered;
	}

	/**
	 * Returns if a recognition rule has possible matches by comparing the
	 * recognition rule blueprint with the change index. Nevertheless the rule
	 * could have no matches also if the function returns <code>true</code>.
	 * 
	 * @param rr
	 *            the recognition rule to analyze.
	 * @return <code>true</code> if the rule has possible matches;
	 *         <code>false</code> otherwise.
	 */
	private boolean matchable(Rule rr) {		
		RecognitionRuleBlueprint blueprint = recognitionRules.get(rr);

		// AddObject:
		for (EClass type : blueprint.addObject.keySet()) {
			Integer blueprintSize = blueprint.addObject.get(type);
			Integer domainSize = liftingGraphDomainMap.getAddObjectDomainSize(type);

			if (domainSize < blueprintSize) {
				return false;
			}
		}
		
		// RemoveObject:
		for (EClass type : blueprint.removeObject.keySet()) {
			Integer blueprintSize = blueprint.removeObject.get(type);
			Integer domainSize = liftingGraphDomainMap.getRemoveObjectDomainSize(type);

			if (domainSize < blueprintSize) {
				return false;
			}
		}
		
		// AddReference:
		for (EReference type : blueprint.addReference.keySet()) {
			Integer blueprintSize = blueprint.addReference.get(type);
			Integer domainSize = liftingGraphDomainMap.getAddReferenceDomainSize(type);

			if (domainSize < blueprintSize) {
				return false;
			}
		}

		// RemoveReference:
		for (EReference type : blueprint.removeReference.keySet()) {
			Integer blueprintSize = blueprint.removeReference.get(type);
			Integer domainSize = liftingGraphDomainMap.getRemoveReferenceDomainSize(type);

			if (domainSize < blueprintSize) {
				return false;
			}
		}
		
		// AttrValueChange:
		for (EAttribute type : blueprint.attributeValueChange.keySet()) {
			Integer blueprintSize = blueprint.attributeValueChange.get(type);
			Integer domainSize = liftingGraphDomainMap.getAttributeValueChangeDomainSize(type);

			if (domainSize < blueprintSize) {
				return false;
			}
		}
		
		return true;
	}
}

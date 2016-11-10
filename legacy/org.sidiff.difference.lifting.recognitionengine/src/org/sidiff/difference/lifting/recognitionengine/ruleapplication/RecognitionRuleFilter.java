package org.sidiff.difference.lifting.recognitionengine.ruleapplication;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.difference.symmetric.util.ChangeIndex;

/**
 * Filters unmatchable recognition rules by counting the atomic changes per type
 * in the difference and in the recognition rule. That means a recognition rule
 * is matchable if there are at least the same count of changes in the
 * difference as in the rule.
 */
public class RecognitionRuleFilter {

	/**
	 * The corresponding change index knowing the amount of low-level changes
	 * per type.
	 */
	private ChangeIndex idx;

	/**
	 * Start recognition rule filter. Filters unmatchable recognition rules by
	 * counting the atomic changes in the difference and in the recognition
	 * rule.
	 * 
	 * @param analysis
	 *            the corresponding change index knowing the amount of low-level
	 *            changes per type.
	 * @param recognitionRules
	 *            all recognition rules to filter.
	 * @return the filtered unmatchable recognition rules.
	 */
	public Set<Rule> filter(ChangeIndex idx, Set<Rule> recognitionRules) {

		// Save index
		this.idx = idx;

		// Filtered rules
		Set<Rule> filtered = new HashSet<Rule>();

		// Analyze recognition rules
		for (Rule rr : recognitionRules) {
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
		RecognitionRuleFilterBlueprint blueprint = new RecognitionRuleFilterBlueprint(rr);

		// AddObject
		for (EClass type : blueprint.addObject.keySet()) {
			Integer blueprintAmount = blueprint.addObject.get(type);
			Integer idxAmount = idx.addObject.get(type);
			if (idxAmount == null) {
				return false;
			}
			if (idxAmount < blueprintAmount) {
				return false;
			}
		}

// FIXME: Note: Filtering of RemoveObjects per type is not that easy due to polymorphism
//		// RemoveObject
//		for (EClass type : blueprint.removeObject.keySet()) {
//			Integer blueprintAmount = blueprint.removeObject.get(type);
//			Integer idxAmount = idx.removeObject.get(type);
//			if (idxAmount == null) {
//				return false;
//			}
//			if (idxAmount < blueprintAmount) {
//				return false;
//			}
//		}
		
		// AddReference
		for (String type : blueprint.addReference.keySet()) {
			Integer blueprintAmount = blueprint.addReference.get(type);
			Integer idxAmount = idx.addReference.get(type);
			if (idxAmount == null) {
				return false;
			}
			if (idxAmount < blueprintAmount) {
				return false;
			}
		}
		
		// RemoveReference
		for (String type : blueprint.removeReference.keySet()) {
			Integer blueprintAmount = blueprint.removeReference.get(type);
			Integer idxAmount = idx.removeReference.get(type);
			if (idxAmount == null) {
				return false;
			}
			if (idxAmount < blueprintAmount) {
				return false;
			}
		}
		
		// AttrValueChange
		for (String type : blueprint.attrValueChange.keySet()) {
			Integer blueprintAmount = blueprint.attrValueChange.get(type);
			Integer idxAmount = idx.attrValueChange.get(type);
			if (idxAmount == null) {
				return false;
			}
			if (idxAmount < blueprintAmount) {
				return false;
			}
		}

		return true;
	}

}

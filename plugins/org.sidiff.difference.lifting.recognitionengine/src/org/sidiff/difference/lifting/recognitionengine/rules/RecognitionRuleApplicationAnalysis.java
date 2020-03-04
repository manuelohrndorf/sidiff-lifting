package org.sidiff.difference.lifting.recognitionengine.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.difference.symmetric.Change;

/**
 * Some utility methods to analyse RecognitionRuleApplications and their matches
 * into the difference.
 * 
 * @author kehrer
 */
public class RecognitionRuleApplicationAnalysis {

	/**
	 * Partitions a set of recognition rule (RR) applications. The
	 * classification criterion for the partitioning is whether two RR
	 * applications refer to the same RR. The "same" here means the same RR
	 * object, i.e. == can serve as comparison operator.
	 * 
	 * @param rrApplication
	 *            the RR collection that is to be classified.
	 * @return A partitioning of rrApplication. Each subset contains RR
	 *         applications which refer to the same recognition rule.
	 */
	public static Collection<List<RuleApplication>> partitionByEqualRule(Collection<RuleApplication> rrApplications) {
		Collection<List<RuleApplication>> res = new ArrayList<>(rrApplications.size());
		for (RuleApplication rrApplication : rrApplications) {

			// do we already have a bucket for this rrApplication
			boolean bucketExisting = false;
			for (List<RuleApplication> bucket : res) {
				if (rrApplication.getRule() == bucket.get(0).getRule()) {
					bucket.add(rrApplication);
					bucketExisting = true;
				}
			}

			if (!bucketExisting) {
				// no bucket so far
				List<RuleApplication> bucket = new LinkedList<>();
				bucket.add(rrApplication);
				res.add(bucket);
			}
		}

		return res;
	}

	/**
	 * Partitions a set of recognition rule (RR) applications. The
	 * classification criterion for the partitioning is whether two RR matches
	 * are equal. Two RR matches are equal if they cover the same low-level
	 * changes.
	 * 
	 * @param rrApplication
	 *            the RR application collection that is to be classified.
	 * @return A partitioning of rrApplication. Each subset contains RR
	 *         applications which are equal, i.e. cover their respective matches
	 *         cover the same low-level changes.
	 */
	public static Collection<List<RuleApplication>> partitionByEqualChanges(Collection<RuleApplication> rrApplications) {
		Collection<List<RuleApplication>> res = new ArrayList<>(rrApplications.size());
		for (RuleApplication rrApplication : rrApplications) {

			// do we already have a bucket for this rrApplication
			boolean bucketExisting = false;
			for (List<RuleApplication> bucket : res) {
				if (compriseEqualChanges(rrApplication, bucket.get(0))) {
					bucket.add(rrApplication);
					bucketExisting = true;
				}
			}

			if (!bucketExisting) {
				// no bucket so far
				List<RuleApplication> bucket = new LinkedList<>();
				bucket.add(rrApplication);
				res.add(bucket);
			}
		}

		return res;
	}

	public static boolean compriseEqualChanges(RuleApplication rrApplication1, RuleApplication rrApplication2) {
		Set<Change> changes1 = getMatchedChanges(rrApplication1);
		Set<Change> changes2 = getMatchedChanges(rrApplication2);
		return changes1.equals(changes2);
	}

	public static Set<Change> getMatchedChanges(RuleApplication rrApplication) {
		Set<Change> res = new HashSet<>();
		addMatchedChanges(res, rrApplication.getCompleteMatch());
		return res;
	}

	private static void addMatchedChanges(Set<Change> matchedChanges, Match rrMatch) {
		// This match
		for (EObject nodeTarget : rrMatch.getNodeTargets()) {
			if (nodeTarget instanceof Change) {
				matchedChanges.add((Change) nodeTarget);
			}
		}

		// Recursion: multi-matches
		Rule kernelRule = rrMatch.getRule();
		for (Rule multiRule : kernelRule.getMultiRules()) {
			for (Match multiMatch : rrMatch.getMultiMatches(multiRule)) {
				addMatchedChanges(matchedChanges, multiMatch);
			}
		}
	}
}

package org.sidiff.difference.model.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import differencemodel.Change;
import differencemodel.Difference;
import differencemodel.SemanticChangeSet;

/**
 * Some utility-functions for analyzing (lifted) model differences.
 */
public class DifferenceAnalysisUtil {

	/**
	 * Calculates the difference of contained atomic changes in all semantic
	 * change sets (SCS) in A to all SCS in B.
	 * 
	 * @param changeSetsA
	 *            the SCS collection A.
	 * @param changeSetsB
	 *            the SCS collection B.
	 * @return (atomic changes in A) - (atomic changes in B) for A is bigger
	 *         than B OR (atomic changes in B) - (atomic changes in A) for B is
	 *         bigger than A.
	 */
	public static Set<Change> getRemainingChanges(Collection<SemanticChangeSet> changeSetsA,
			Collection<SemanticChangeSet> changeSetsB) {
		Set<Change> aChanges = new HashSet<Change>();
		Set<Change> bChanges = new HashSet<Change>();

		// Collect all atomic changes of the semantic change sets
		for (SemanticChangeSet csA : changeSetsA) {
			aChanges.addAll(csA.getChanges());
		}

		for (SemanticChangeSet csB : changeSetsB) {
			bChanges.addAll(csB.getChanges());
		}

		// Return (bigger set - smaller set)
		if (aChanges.size() > bChanges.size()) {
			aChanges.removeAll(bChanges);
			return aChanges;
		} else {
			bChanges.removeAll(aChanges);
			return bChanges;
		}
	}
	
	/**
	 * Returns all atomic changes which are not covered by semantic change sets.
	 * 
	 * @param difference
	 *            the difference containing atomic changes and semantic change sets.
	 * @return all atomic changes which are not covered by semantic change sets.
	 */
	public static Set<Change> getRemainingChanges(Difference difference) {
		return getRemainingChanges(difference, difference.getChangeSets());
	}

	/**
	 * Calculates the difference of contained atomic changes in the difference
	 * to all atomic changes in B.
	 * 
	 * @param difference
	 *            the difference containing atomic changes.
	 * @param changeSetsB
	 *            SCS collection B.
	 * @return (atomic changes in difference) - (atomic changes in B) for
	 *         difference atomic change count is bigger than B OR (atomic
	 *         changes in B) - (atomic changes in difference) for B is bigger
	 *         than difference.
	 */
	public static Set<Change> getRemainingChanges(Difference difference,
			Collection<SemanticChangeSet> changeSetsB) {
		Set<Change> aChanges = new HashSet<Change>();
		Set<Change> bChanges = new HashSet<Change>();

		// Collect all atomic changes of the semantic change sets
		aChanges.addAll(difference.getChanges());

		for (SemanticChangeSet csB : changeSetsB) {
			bChanges.addAll(csB.getChanges());
		}

		// Return (bigger SCS - smaller SCS)
		if (aChanges.size() > bChanges.size()) {
			aChanges.removeAll(bChanges);
			return aChanges;
		} else {
			bChanges.removeAll(aChanges);
			return bChanges;
		}
	}

	/**
	 * Compares to semantic change sets (SCS) by their containing atomic
	 * changes.
	 * 
	 * @param csA
	 *            the SCS A.
	 * @param csB
	 *            the SCS B.
	 * @return true if A and B are equal; false otherwise.
	 */
	public static boolean isEqual(SemanticChangeSet csA, SemanticChangeSet csB) {
		int i = getOverlapping(csA, csB);

		if (i == csA.getChanges().size() && (i == csB.getChanges().size())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns the first equal semantic change set (SCS) in the collection to
	 * the given SCS.
	 * 
	 * @param cs
	 *            the SCS that will be tested against the SCS collection.
	 * @param changeSets
	 *            the SCS collection the given SCS will be tested against.
	 * @return true if there is an equal SCS in the collection; false otherwise.
	 */
	public static SemanticChangeSet hasEqual(SemanticChangeSet cs,
			Collection<SemanticChangeSet> changeSets) {
		for (SemanticChangeSet csComp : changeSets) {
			if (isEqual(cs, csComp)) {
				return csComp;
			}
		}
		return null;
	}

	/**
	 * Search for all semantic change sets (SCS) in the collection which are
	 * overlapping with the given SCS.
	 * 
	 * @param cs
	 *            the SCS that will be tested against the collection.
	 * @param changeSets
	 *            the collection the given SCS will be tested against.
	 * @return
	 */
	public static Set<SemanticChangeSet> getOverlappingChangeSets(SemanticChangeSet cs,
			Collection<SemanticChangeSet> changeSets) {
		Set<SemanticChangeSet> overlapping = new HashSet<SemanticChangeSet>();

		for (SemanticChangeSet csComp : changeSets) {
			if ((csComp != cs) && (isOverlapping(cs, csComp))) {
				overlapping.add(csComp);
			}
		}
		return overlapping;
	}

	/**
	 * Returns all atomic changes which are in semantic change set (SCS) A and
	 * also in SCS B.
	 * 
	 * @param csA
	 *            the SCS A.
	 * @param csB
	 *            the SCS B.
	 * @return the overlapping atomic changes.
	 */
	public static Set<Change> getOverlappingChanges(SemanticChangeSet csA, SemanticChangeSet csB) {
		Set<Change> overlapping = new HashSet<Change>();

		for (Change changeA : csA.getChanges()) {
			if (csB.getChanges().contains(changeA)) {
				overlapping.add(changeA);
			}
		}
		return overlapping;
	}

	/**
	 * Counts all overlapping atomic changes of semantic change set (SCS) A and
	 * B.
	 * 
	 * @param csA
	 *            the SCS A.
	 * @param csB
	 *            the SCS B.
	 * @return the count of all overlapping atomic changes.
	 */
	public static int getOverlapping(SemanticChangeSet csA, SemanticChangeSet csB) {
		int i = 0;

		for (Change changeA : csA.getChanges()) {
			if (csB.getChanges().contains(changeA)) {
				i++;
			}
		}
		return i;
	}

	/**
	 * Indicates if semantic change set (SCS) A has overlapping atomic changes
	 * with SCS B.
	 * 
	 * @param csA
	 *            the SCS A.
	 * @param csB
	 *            the SCS B.
	 * @return true if there are overlapping atomic changes; false otherwise.
	 */
	public static boolean isOverlapping(SemanticChangeSet csA, SemanticChangeSet csB) {
		int i = getOverlapping(csA, csB);

		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Indicates if semantic change set (SCS) has overlapping atomic change.
	 * 
	 * @param scs the set of SCS to check
	 * @return true if there are overlapping atomic changes; false otherwise.
	 */
	public static boolean hasOverlapping(Set<SemanticChangeSet> scs) {
		for(SemanticChangeSet cs : scs) {
			for(SemanticChangeSet csComp : scs) {
				if ((cs != csComp) && (isOverlapping(cs, csComp))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Indicates if semantic change set (SCS) A is partially overlapping with
	 * SCS B. That means SCS A is no subset or superset of SCS B and vice versa.
	 * 
	 * @param csA
	 *            the SCS A.
	 * @param csB
	 *            the SCS B.
	 * @return true if SCS A is partially overlapping with SCS B; false
	 *         otherwise.
	 */
	public static boolean isPartiallyOverlapping(SemanticChangeSet csA, SemanticChangeSet csB) {
		int i = getOverlapping(csA, csB);

		if ((i > 0) && (csA.getChanges().size() - i != 0) && (csB.getChanges().size() - i != 0)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Indicates if the given semantic change set (SCS) A has a partially
	 * overlapping SCS B in the collection. That means SCS A is no subset or
	 * superset of SCS B and vice versa.
	 * 
	 * @param cs
	 *            the SCS A which will be tested against the SCS collection.
	 * @param changeSets
	 *            the SCS collection SCS A will be tested against.
	 * @return true if SCS A is partially overlapping with any SCS in the
	 *         collection; false otherwise.
	 */
	public static boolean hasPartiallyOverlapping(SemanticChangeSet cs,
			Collection<SemanticChangeSet> changeSets) {
		for (SemanticChangeSet csComp : changeSets) {
			if ((cs != csComp) && isPartiallyOverlapping(cs, csComp)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Indicates that the inner semantic change set (SCS) is a proper (or
	 * strict) subset of the outer SCS.
	 * 
	 * @param innerCS
	 *            the inner SCS.
	 * @param outerCS
	 *            the outer SCS.
	 * @return true if the inner SCS is a proper subset of the outer SCS; false
	 *         otherwise.
	 */
	public static boolean isProperlyNested(SemanticChangeSet innerCS, SemanticChangeSet outerCS) {
		if (outerCS.getChanges().containsAll(innerCS.getChanges())
				&& innerCS.getChanges().size() != outerCS.getChanges().size()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Indicates that the given semantic change set (SCS) has a proper (or
	 * strict) subset in the given SCS collection.
	 * 
	 * @param cs
	 *            the SCS that will be tested against the SCS collection.
	 * @param changeSets
	 *            the SCS collection the given SCS will be tested against.
	 * @return true if the given SCS has a proper (or strict) subset in the
	 *         given SCS collection; false otherwise.
	 */
	public static boolean isProperlyNested(SemanticChangeSet cs,
			Collection<SemanticChangeSet> changeSets) {
		for (SemanticChangeSet csComp : changeSets) {
			if ((cs != csComp) && isProperlyNested(cs, csComp)) {
				return true;
			}
		}
		return false;
	}
}

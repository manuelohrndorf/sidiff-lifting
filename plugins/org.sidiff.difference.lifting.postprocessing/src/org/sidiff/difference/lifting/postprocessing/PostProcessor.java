package org.sidiff.difference.lifting.postprocessing;

import static org.sidiff.difference.symmetric.util.DifferenceAnalysisUtil.getGreatest;
import static org.sidiff.difference.symmetric.util.DifferenceAnalysisUtil.getRemainingChanges;
import static org.sidiff.difference.symmetric.util.DifferenceAnalysisUtil.hasOverlapping;
import static org.sidiff.difference.symmetric.util.DifferenceAnalysisUtil.isPartiallyOverlaid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngine;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.util.ChangeSetPriorityComparator;
import org.sidiff.difference.symmetric.util.DifferenceAnalysisUtil;

/**
 * The post processing algorithm eliminates overlapping semantic change sets. The prior goal of post
 * processing is to produce a lifted difference that cover (or group) as many atomic changes (by
 * semantic change sets) as possibly. The secondary goal is to keep the largest semantic change
 * sets.
 */
public class PostProcessor {

	/**
	 * The RecognitionEngine instance that was used to semantically lift a difference
	 */
	private RecognitionEngine recognitionEngine;

	/**
	 * The difference
	 */
	private SymmetricDifference difference;

	/**
	 * PCS_D is initialized with all semantic change sets of the entry difference. While post
	 * processing it is step by step reduced until it is empty. All not overlapping semantic change
	 * sets are kept in the PCS_min.
	 */
	private List<SemanticChangeSet> PCS_D;

	/**
	 * Collects the minimal not overlapping semantic change sets.
	 */
	private Set<SemanticChangeSet> PCS_min;

	/**
	 * The combination limit per thread for the overlapping change set areas.
	 */
	private long limit = 100000;

	/**
	 * This waring is thrown when the power set of overlapping semantic change sets (count) exceeds
	 * the combination <code>limit</code>
	 */
	private static final String WARNING_POST_PROCESSING_0 = "Warning: Too much overlapping semantic change sets! Solution is maybe not optimal!";

	/**
	 * Comparator is needed sort the semantic change sets by the size of their contained atomic
	 * changes.
	 */
	private Comparator<SemanticChangeSet> compCS = new Comparator<SemanticChangeSet>() {
		@Override
		public int compare(SemanticChangeSet cs1, SemanticChangeSet cs2) {
			return cs2.getChanges().size() - cs1.getChanges().size();
		}
	};

	/**
	 * Creates a {@link PostProcessor}
	 * 
	 * @param recognitionEngine
	 *            The RecognitionEngine instance that was used to semantically lift a difference
	 */
	public PostProcessor(RecognitionEngine recognitionEngine) {
		this.recognitionEngine = recognitionEngine;
		this.difference = recognitionEngine.getDifference();
	}

	/**
	 * Creates a {@link PostProcessor}
	 * 
	 * @param difference
	 *            the difference containing the semantic change sets to optimize.
	 */
	public PostProcessor(SymmetricDifference difference) {
		this.difference = difference;
	}

	/**
	 * The post processing algorithm eliminates overlapping semantic change sets. The prior goal of
	 * post processing is to produce a lifted difference that cover (or group) as many atomic
	 * changes (by semantic change sets) as possibly. The secondary goal is to keep the largest
	 * semantic change sets.
	 * 
	 * @param difference
	 *            the entry difference (with overlapping semantic change sets). All overlapping
	 *            semantic change sets will be removed from this difference object.
	 */
	public void postProcess() {
		postProcess(true);
	}

	/**
	 * The post processing algorithm eliminates overlapping semantic change sets. The prior goal of
	 * post processing is to produce a lifted difference that cover (or group) as many atomic
	 * changes (by semantic change sets) as possibly. The secondary goal is to keep the largest
	 * semantic change sets.
	 * 
	 * @param difference
	 *            the entry difference (with overlapping semantic change sets). All overlapping
	 *            semantic change sets will be removed from this difference object.
	 * @param analyzeStructre
	 *            <code>true</code> to analyze the semantic change set structure of the difference
	 *            first; <code>false</code> otherwise.
	 * @param saveUnusedChangeSets
	 *            <code>true</code> to save the unused change sets in the difference;
	 *            <code>false</code> to remove the unused change sets form the difference.
	 */
	public void postProcess(boolean saveUnusedChangeSets) {

		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "--------------- ANALYZE DIFFERENCE STRUCTURE ---------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		DifferenceAnalysisUtil.analyzeDifferenceStructure(difference);
		LogUtil.log(LogEvent.DEBUG, DifferenceAnalysisUtil.printDifferenceStructure(difference));
		
		
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "----------------- POSTPROCESSING ALGORITHM -----------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		// Reset previous post-processing
//		difference.getChangeSets().addAll(difference.getUnusedChangeSets());
//		difference.getUnusedChangeSets().clear();
		assert(difference.getUnusedChangeSets().isEmpty());
		
		// Initialize PCS_D with all semantic change sets of the entry difference
		PCS_D = new LinkedList<SemanticChangeSet>();

		
		// Remove empty Semantic Change Sets:
		// Necessary if only the Kernel-Rule matches, but no Multi-Rule.
		for (Iterator<SemanticChangeSet> iterator = difference.getChangeSets().iterator(); iterator.hasNext();) {
			SemanticChangeSet nextCS = iterator.next();
			if (nextCS.getChanges().isEmpty()){
				recognitionEngine.removeMatches(nextCS);
				difference.getNotOverlappings().remove(nextCS);								
				iterator.remove();
			}
		}
			
		// Remove equal semantic change sets. Two semantic change sets are
		// equal if they have the same atomic changes.
		Collection<List<SemanticChangeSet>> equalCS = DifferenceAnalysisUtil.classifyEqual(difference.getChangeSets());
		Comparator<SemanticChangeSet> csPrioComparator = new ChangeSetPriorityComparator();
		for (List<SemanticChangeSet> changeSets : equalCS) {
			Collections.sort(changeSets, csPrioComparator);
			// the cs with the highest priority is at the end of the sorted list
			PCS_D.add(changeSets.get(changeSets.size()-1)); 
		}
		
		// Sort semantic change sets by the size of their change count.
		Collections.sort(PCS_D, compCS);

		PCS_min = new HashSet<SemanticChangeSet>();

		// Step 1 in concept paper
		findNotOverlapping();

		// Step 2 in concept paper
		findProperlyNested();

		// Step 3 in concept paper
		findPartiallyOverlaids();
		
		List<List<SemanticChangeSet>> groupedSCS = findGroupedSCS();
		
		greedyFindMinimalSetPartitioning(groupedSCS);
		findMinimalSetPatitioning(groupedSCS);

		// Print report
		LogUtil.log(LogEvent.NOTICE, "Detected semantic change sets: " + difference.getChangeSets().size());
		LogUtil.log(LogEvent.NOTICE, "Retained semantic change sets: " + PCS_min.size());
		LogUtil.log(LogEvent.NOTICE,
				"Removed semantic change sets: " + (difference.getChangeSets().size() - PCS_min.size()));

		int uncoveredBefore = getRemainingChanges(difference, difference.getChangeSets()).size();
		int uncoveredAfter = getRemainingChanges(difference, PCS_min).size();

		/*TODO Revise assertions
		 * commented out for debugging purposes
		 * 15.08.2013
		 * 
		 * assert (uncoveredBefore == 0) : "uncoveredBefore != 0";
		 * assert (uncoveredAfter == 0) : "uncoveredAfter != 0";
		 */
		
		
		LogUtil.log(LogEvent.NOTICE, "Coverd atomic changes before post processing: "
				+ (difference.getChanges().size() - uncoveredBefore));
		LogUtil.log(LogEvent.NOTICE, "Coverd atomic changes after post processing: "
				+ (difference.getChanges().size() - uncoveredAfter));
		LogUtil.log(LogEvent.NOTICE, "Still uncovered atomic changes: " + uncoveredAfter);

		// Now PCS_min should include the proper change sets. Move all change
		// sets which are not included in PCS_min to 'unused change sets'.
		retainPCS_min(saveUnusedChangeSets);
		
	}

	
	/**
	 * Search for all semantic change sets that are not overlapping with another semantic change set
	 * (SCS) and add this SCS to the PCS_min set. Now all SCS in PCS_min are removed from PCS_D.
	 */
	private void findNotOverlapping() {
		PCS_min.addAll(difference.getNotOverlappings());
		PCS_D.removeAll(difference.getNotOverlappings());
	}

	/**
	 * Search for all semantic change sets (SCS) that are proper subsets of a larger SCS. Than
	 * remove the subsets from the larger SCS. Note that the larger SCS may not have other partially
	 * overlapping SCS.
	 */
	private void findProperlyNested() {
		Set<SemanticChangeSet> nestedCS = new HashSet<SemanticChangeSet>();

		for (SemanticChangeSet cs : PCS_D) {
			// SCS is not proper subset of another SCS and is not partially overlapping with another SCS.
			if ((cs.getSupersets().size() == 0) && (cs.getPartiallyOverlappings().size() == 0)) {
				PCS_min.add(cs);
				nestedCS.addAll(cs.getSubsets());
			}
		}

		// Remove all nested and kept SCS from PCS_D
		PCS_D.removeAll(nestedCS);
		PCS_D.removeAll(PCS_min);
	}

	/**
	 * Search for all semantic change sets (SCS) that covers (or groups) atomic changes which are
	 * not covered by any other SCS. Keep that SCS, if it is possibly to remove all overlapping SCS
	 * without producing new uncovered (or ungrouped) atomic changes.
	 */
	private void findPartiallyOverlaids() {

		for (int i = 0; i < PCS_D.size(); i++) {
			SemanticChangeSet cs = PCS_D.get(i);

			Collection<SemanticChangeSet> overlappingChangeSets = new HashSet<SemanticChangeSet>();
			overlappingChangeSets.addAll(cs.getPartiallyOverlappings());
			overlappingChangeSets.addAll(cs.getSubsets());
			overlappingChangeSets.addAll(cs.getSupersets());
			overlappingChangeSets.retainAll(PCS_D);

			// If this semantic change set part isn't fully covered by the other semantic
			// change sets then we have to take it to cover the full range of differences.
			if (isPartiallyOverlaid(cs, overlappingChangeSets)) {
				// Take this semantic change set only if that action 
				// doesn't produce uncovered atomic changes.
				boolean uncoveredAtomics = false;

				for (SemanticChangeSet removeCS : overlappingChangeSets) {
					Collection<SemanticChangeSet> removeCSOverlappings = new HashSet<SemanticChangeSet>();
					removeCSOverlappings.addAll(removeCS.getPartiallyOverlappings());
					removeCSOverlappings.addAll(removeCS.getSubsets());
					removeCSOverlappings.addAll(removeCS.getSupersets());
					removeCSOverlappings.retainAll(PCS_D);

					if (isPartiallyOverlaid(removeCS, removeCSOverlappings)) {
						uncoveredAtomics = true;
						break;
					}
				}

				if (!uncoveredAtomics) {
					// Move current partially overlaid change set to PCS_min
					PCS_min.add(cs);
					PCS_D.remove(cs);

					// Remove all change sets that overlap with the current change set
					PCS_D.removeAll(overlappingChangeSets);

					// Conditions have changed start loop again
					i = -1;
				}
			}
		}
	}
	
	/**
	 * Calculate the non overlapping change sets for all grouped SCS that
	 * exceeds the combination limit using a greedy algorithm, i.e. prefer the
	 * greatest SCS.
	 * 
	 * @param allGroupedSCS
	 *            a list of semantic change set groups. This groups are disjoint
	 *            (nonoverlapping) sets to each other.
	 */
	private void greedyFindMinimalSetPartitioning(List<List<SemanticChangeSet>> allGroupedSCS) {

		Iterator<List<SemanticChangeSet>> it = allGroupedSCS.iterator();
		
		while(it.hasNext()) {
			List<SemanticChangeSet> groupedSCS = it.next();
			
			if(Math.pow(2, groupedSCS.size()) > limit) {
				// Greedy algorithm:
				List<SemanticChangeSet> nonoverlappingGroup = new ArrayList<SemanticChangeSet>();
				
				while(groupedSCS.size() > 0) {
					SemanticChangeSet cs = getGreatest(groupedSCS);
					nonoverlappingGroup.add(cs);
					groupedSCS.remove(cs);
					
					// Remove overlapping SCS
					groupedSCS.removeAll(cs.getPartiallyOverlappings());
					groupedSCS.removeAll(cs.getSubsets());
				}
				
				it.remove();
				PCS_min.addAll(nonoverlappingGroup);
			}
		}
	}

	/**
	 * Calculates the best combination of overlapping semantic change sets. The prior goal is to
	 * produce a combination that cover (or group) as many atomic changes (by semantic change sets)
	 * as possibly. The secondary goal is to keep the largest semantic change sets.
	 */
	private void findMinimalSetPatitioning(List<List<SemanticChangeSet>> allGroupedSCS) {
		List<FindBestCombination> threadList = new LinkedList<FindBestCombination>();

		// Start calculation threads
		for (List<SemanticChangeSet> changeSets : allGroupedSCS) {
			FindBestCombination combThread = new FindBestCombination(changeSets, limit);
			threadList.add(combThread);
			combThread.start();
		}

		// Wait for threads to finish
		for (FindBestCombination combThread : threadList) {
			try {
				combThread.join();
				PCS_min.addAll(combThread.getBest_PCS_min_result());
				PCS_D.removeAll(combThread.getPCS_D_part());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Search for semantic change sets (SCS) which are overlapping with each other and group this
	 * SCS. Note that each SCS is only in one group. Do this for all SCS in PCS_D. After that we
	 * have a list of semantic change set groups. This groups are disjoint (nonoverlapping) sets to
	 * each other.
	 * 
	 * @return a list of semantic change set groups. This groups are disjoint (nonoverlapping) sets
	 *         to each other.
	 */
	private List<List<SemanticChangeSet>> findGroupedSCS() {

		List<List<SemanticChangeSet>> allGroupedSCS = new LinkedList<List<SemanticChangeSet>>();

		while (PCS_D.size() > 0) {
			SemanticChangeSet cs = PCS_D.get(0);

			// Initialize the grouped set with the first remaining SCS in PCS_D
			List<SemanticChangeSet> groupedSCS = new LinkedList<SemanticChangeSet>();
			groupedSCS.add(cs);
			PCS_D.remove(cs);

			// Initialize the overlappingSet with the first set in PCS_D
			Set<SemanticChangeSet> overlappingSet = new HashSet<SemanticChangeSet>();
			overlappingSet.add(cs);

			while (overlappingSet.size() > 0) {

				Set<SemanticChangeSet> deeperOverlappingSet = new HashSet<SemanticChangeSet>();

				// Find all sets overlapping with the change sets in overlappingSet
				for (SemanticChangeSet overlappingCS : overlappingSet) {
					deeperOverlappingSet.addAll(overlappingCS.getPartiallyOverlappings());
					deeperOverlappingSet.addAll(overlappingCS.getSubsets());
					deeperOverlappingSet.addAll(overlappingCS.getSupersets());
					deeperOverlappingSet.retainAll(PCS_D);
				}

				// It is a like a recursive search for the next overlapping sets
				groupedSCS.addAll(overlappingSet);
				PCS_D.removeAll(overlappingSet);
				overlappingSet = deeperOverlappingSet;
			}

			allGroupedSCS.add(groupedSCS);
		}

		return allGroupedSCS;
	}

	/**
	 * Retains only the SemanticChangeSets in the difference that are contained in PCS_min. In other
	 * words, move all the SemanticChangeSets that are not contained in PCS_min to the 'unused
	 * change sets' of the difference.
	 * 
	 * Note: This method also keeps match information of the RecognitionEngine (i.e.
	 * recognitionRuleMatch and editRuleMatch) up to date. That is, we remove the match information
	 * for change sets that are deleted during postprocessing.
	 */
	private void retainPCS_min(boolean saveUnusedChangeSets) {
		for (Iterator<SemanticChangeSet> iterator = difference.getChangeSets().iterator(); iterator.hasNext();) {
			SemanticChangeSet scs = iterator.next();

			if (!PCS_min.contains(scs)) {
				if (recognitionEngine != null) {
					recognitionEngine.removeMatches(scs);
				}

				// Remove SCS
				iterator.remove();
				
				if (saveUnusedChangeSets) {
					// Move SCS to 'unused change sets'
					difference.getUnusedChangeSets().add(scs);
				}
			}
		}
	}

	/**
	 * A tread class that calculates the best combination of overlapping semantic change sets. The
	 * prior goal is to produce a combination that cover (or group) as many atomic changes (by
	 * semantic change sets) as possibly. The secondary goal is to keep the largest semantic change
	 * sets.
	 */
	private class FindBestCombination extends Thread {

		public FindBestCombination(List<SemanticChangeSet> PCS_D_part, long limit) {
			super();
			this.PCS_D_part = PCS_D_part;
			this.limit = limit;
		}

		private List<SemanticChangeSet> PCS_D_part;
		private Set<SemanticChangeSet> best_PCS_min_result;
		private long limit;

		@Override
		public void run() {

			// Check limit
			if (Math.pow(2, PCS_D_part.size()) > limit) {
				LogUtil.log(LogEvent.WARNING, WARNING_POST_PROCESSING_0);
			}

			// Start calculation
			best_PCS_min_result = new HashSet<SemanticChangeSet>();
			int bestRemaining = Integer.MAX_VALUE;
			int bestCompression = Integer.MAX_VALUE;

			// Get power set of PCS_D_part
			PowerSet<SemanticChangeSet> powerSetCalculator = new PowerSet<SemanticChangeSet>(limit);
			LinkedHashSet<LinkedHashSet<SemanticChangeSet>> pcs_d_powerSet = powerSetCalculator.powerSet(PCS_D_part);

			for (LinkedHashSet<SemanticChangeSet> subset : pcs_d_powerSet) {

				// Skip overlapping
				if (!hasOverlapping(subset)) {

					// Rate subset
					int remaining = getRemainingChanges(subset, PCS_D_part).size();
					int compression = subset.size();

					if (remaining < bestRemaining) {
						bestRemaining = remaining;
						bestCompression = compression;
						best_PCS_min_result = subset;
					} else if (remaining == bestRemaining) {
						if (compression < bestCompression) {
							bestCompression = compression;
							best_PCS_min_result = subset;
						}
					}
				}
			}
		}

		public List<SemanticChangeSet> getPCS_D_part() {
			return PCS_D_part;
		}

		public Set<SemanticChangeSet> getBest_PCS_min_result() {
			return best_PCS_min_result;
		}
	}

	/*
	 * Getter-Setter
	 */

	/**
	 * @return the combination limit per thread for the overlapping change set areas.
	 */
	public long getLimit() {
		return limit;
	}

	/**
	 * Set the new combination limit per thread for the overlapping change set areas.
	 * 
	 * @param limit
	 *            the new combination limit per thread for the overlapping change set areas.
	 */
	public void setLimit(long limit) {
		this.limit = limit;
	}
}

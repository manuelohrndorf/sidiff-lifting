package org.sidiff.editrule.analysis.criticalpairs.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.sidiff.editrule.rulebase.PotentialAttributeConflict;
import org.sidiff.editrule.rulebase.PotentialConflict;
import org.sidiff.editrule.rulebase.PotentialEdgeConflict;
import org.sidiff.editrule.rulebase.PotentialNodeConflict;

/**
 * Simple container class that holds different kinds of potential conflicts.
 * 
 */

public class PotentialRuleConflicts {

	private Set<PotentialNodeConflict> potentialNodeConflicts;
	private Set<PotentialEdgeConflict> potentialEdgeConflicts;
	private Set<PotentialAttributeConflict> potentialAttributeConflicts;

	public Set<PotentialNodeConflict> getPotentialNodeConflicts() {
		if (potentialNodeConflicts == null) {
			potentialNodeConflicts = new HashSet<PotentialNodeConflict>();
		}

		return Collections.unmodifiableSet(potentialNodeConflicts);
	}

	public Set<PotentialEdgeConflict> getPotentialEdgeConflicts() {
		if (potentialEdgeConflicts == null) {
			potentialEdgeConflicts = new HashSet<PotentialEdgeConflict>();
		}

		return Collections.unmodifiableSet(potentialEdgeConflicts);
	}

	public Set<PotentialAttributeConflict> getPotentialAttributeConflicts() {
		if (potentialAttributeConflicts == null) {
			potentialAttributeConflicts = new HashSet<PotentialAttributeConflict>();
		}

		return Collections.unmodifiableSet(potentialAttributeConflicts);
	}

	public Set<PotentialConflict> getPotentialConflicts() {
		Set<PotentialConflict> potDeps = new HashSet<PotentialConflict>();
		potDeps.addAll(getPotentialNodeConflicts());
		potDeps.addAll(getPotentialEdgeConflicts());
		potDeps.addAll(getPotentialAttributeConflicts());

		return Collections.unmodifiableSet(potDeps);
	}

	public void addAllPNCs(Set<PotentialNodeConflict> pncs) {
		if (potentialNodeConflicts == null) {
			potentialNodeConflicts = new HashSet<PotentialNodeConflict>();
		}

		for (PotentialNodeConflict pnc : pncs) {

				potentialNodeConflicts.add(pnc);
			
		}
	}

	public void addAllPECs(Set<PotentialEdgeConflict> pecs) {
		if (potentialEdgeConflicts == null) {
			potentialEdgeConflicts = new HashSet<PotentialEdgeConflict>();
		}

		for (PotentialEdgeConflict pec : pecs) {

				potentialEdgeConflicts.add(pec);
			
		}
	}

	public void addAllPACs(Set<PotentialAttributeConflict> pacs) {
		if (potentialAttributeConflicts == null) {
			potentialAttributeConflicts = new HashSet<PotentialAttributeConflict>();
		}

		for (PotentialAttributeConflict pac : pacs) {
		
				potentialAttributeConflicts.add(pac);
			
		}
	}

	public void add(PotentialRuleConflicts deps) {
		addAllPNCs(deps.getPotentialNodeConflicts());
		addAllPECs(deps.getPotentialEdgeConflicts());
		addAllPACs(deps.getPotentialAttributeConflicts());
	}

}

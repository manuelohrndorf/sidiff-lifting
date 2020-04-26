package org.sidiff.editrule.analysis.criticalpairs.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.sidiff.editrule.rulebase.PotentialAttributeConflict;
import org.sidiff.editrule.rulebase.PotentialConflict;
import org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict;
import org.sidiff.editrule.rulebase.PotentialEdgeConflict;
import org.sidiff.editrule.rulebase.PotentialNodeConflict;

/**
 * Simple container class that holds different kinds of potential conflicts.
 */
public class PotentialRuleConflicts {

	private Set<PotentialNodeConflict> potentialNodeConflicts = new HashSet<>();
	private Set<PotentialEdgeConflict> potentialEdgeConflicts = new HashSet<>();
	private Set<PotentialDanglingEdgeConflict> potentialDanglingEdgeConflicts = new HashSet<PotentialDanglingEdgeConflict>();

	private Set<PotentialAttributeConflict> potentialAttributeConflicts = new HashSet<>();

	public Set<PotentialNodeConflict> getPotentialNodeConflicts() {
		return Collections.unmodifiableSet(potentialNodeConflicts);
	}

	public Set<PotentialEdgeConflict> getPotentialEdgeConflicts() {
		return Collections.unmodifiableSet(potentialEdgeConflicts);
	}

	public Set<PotentialAttributeConflict> getPotentialAttributeConflicts() {
		return Collections.unmodifiableSet(potentialAttributeConflicts);
	}

	public Set<PotentialConflict> getPotentialConflicts() {
		return Stream.of(potentialNodeConflicts, potentialEdgeConflicts, potentialAttributeConflicts)
			.flatMap(Collection::stream)
			.collect(Collectors.toSet());
	}

	public void addAllPNCs(Set<PotentialNodeConflict> pncs) {
		potentialNodeConflicts.addAll(pncs);
	}

	public void addAllPECs(Set<PotentialEdgeConflict> pecs) {
		potentialEdgeConflicts.addAll(pecs);
	}

	public void addAllPACs(Set<PotentialAttributeConflict> pacs) {
		potentialAttributeConflicts.addAll(pacs);
	}

	public void add(PotentialRuleConflicts deps) {
		addAllPNCs(deps.getPotentialNodeConflicts());
		addAllPECs(deps.getPotentialEdgeConflicts());
		addAllPDECs(deps.getPotentialDanglingEdgeConflicts());
		addAllPACs(deps.getPotentialAttributeConflicts());
	}

	public Set<PotentialDanglingEdgeConflict> getPotentialDanglingEdgeConflicts() {
		return potentialDanglingEdgeConflicts;
	}

	public void addAllPDECs(Set<PotentialDanglingEdgeConflict> createForbidDanglingEdgePotCons) {
		potentialDanglingEdgeConflicts.addAll(potentialDanglingEdgeConflicts);
		
	}
}
package org.sidiff.editrule.analysis.criticalpairs.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.sidiff.editrule.rulebase.PotentialAttributeDependency;
import org.sidiff.editrule.rulebase.PotentialDanglingEdgeConflict;
import org.sidiff.editrule.rulebase.PotentialDanglingEdgeDependency;
import org.sidiff.editrule.rulebase.PotentialDependency;
import org.sidiff.editrule.rulebase.PotentialEdgeDependency;
import org.sidiff.editrule.rulebase.PotentialNodeDependency;

/**
 * Simple container class that holds different kinds of potential dependencies.
 */
public class PotentialRuleDependencies {

	private Set<PotentialNodeDependency> potentialNodeDependencies = new HashSet<>();
	private Set<PotentialEdgeDependency> potentialEdgeDependencies = new HashSet<>();
	private Set<PotentialAttributeDependency> potentialAttributeDependencies = new HashSet<>();
	
	private Set<PotentialDanglingEdgeDependency> potentialDanglingEdgeDependencies = new HashSet<PotentialDanglingEdgeDependency>();

	public Set<PotentialNodeDependency> getPotentialNodeDependencies() {
		return Collections.unmodifiableSet(potentialNodeDependencies);
	}

	public Set<PotentialEdgeDependency> getPotentialEdgeDependencies() {
		return Collections.unmodifiableSet(potentialEdgeDependencies);
	}

	public Set<PotentialDanglingEdgeDependency> getPotentialDanglingEdgeDependencies() {
		return Collections.unmodifiableSet(potentialDanglingEdgeDependencies);
	}
	
	public Set<PotentialAttributeDependency> getPotentialAttributeDependencies() {
		return Collections.unmodifiableSet(potentialAttributeDependencies);
	}

	public Set<PotentialDependency> getPotentialDependencies() {
		return Stream.of(potentialNodeDependencies, potentialEdgeDependencies, potentialDanglingEdgeDependencies, potentialAttributeDependencies)
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}

	public void addAllPNDs(Set<PotentialNodeDependency> pnds) {
		potentialNodeDependencies.addAll(pnds);
	}
	
	

	public void addAllPEDs(Set<PotentialEdgeDependency> peds) {
		potentialEdgeDependencies.addAll(peds);
	}

	public void addAllPDEDs(Set<PotentialDanglingEdgeDependency> pdeds) {
		potentialDanglingEdgeDependencies.addAll(pdeds);
	}

	
	public void addAllPADs(Set<PotentialAttributeDependency> pads) {
		potentialAttributeDependencies.addAll(pads);
	}

	public void add(PotentialRuleDependencies deps) {
		addAllPNDs(deps.getPotentialNodeDependencies());
		addAllPEDs(deps.getPotentialEdgeDependencies());
		addAllPDEDs(deps.getPotentialDanglingEdgeDependencies());
		addAllPADs(deps.getPotentialAttributeDependencies());
	}
}

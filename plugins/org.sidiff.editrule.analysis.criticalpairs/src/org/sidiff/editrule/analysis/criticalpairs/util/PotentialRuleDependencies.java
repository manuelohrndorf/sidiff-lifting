package org.sidiff.editrule.analysis.criticalpairs.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.sidiff.editrule.rulebase.PotentialAttributeDependency;
import org.sidiff.editrule.rulebase.PotentialDependency;
import org.sidiff.editrule.rulebase.PotentialDependencyKind;
import org.sidiff.editrule.rulebase.PotentialEdgeDependency;
import org.sidiff.editrule.rulebase.PotentialNodeDependency;

/**
 * Simple container class that holds different kinds of potential dependencies.
 * 
 */
public class PotentialRuleDependencies {

	private Set<PotentialNodeDependency> potentialNodeDependencies = new HashSet<>();
	private Set<PotentialEdgeDependency> potentialEdgeDependencies = new HashSet<>();
	private Set<PotentialAttributeDependency> potentialAttributeDependencies = new HashSet<>();

	public Set<PotentialNodeDependency> getPotentialNodeDependencies() {
		return Collections.unmodifiableSet(potentialNodeDependencies);
	}

	public Set<PotentialEdgeDependency> getPotentialEdgeDependencies() {
		return Collections.unmodifiableSet(potentialEdgeDependencies);
	}

	public Set<PotentialAttributeDependency> getPotentialAttributeDependencies() {
		return Collections.unmodifiableSet(potentialAttributeDependencies);
	}

	public Set<PotentialDependency> getPotentialDependencies() {
		return Stream.of(potentialNodeDependencies, potentialEdgeDependencies, potentialAttributeDependencies)
				.flatMap(Collection::stream)
				.collect(Collectors.toSet());
	}

	public void addAllPNDs(Set<PotentialNodeDependency> pnds) {
		for (PotentialNodeDependency pnd : pnds) {
			if (!exclude(pnd)) {
				potentialNodeDependencies.add(pnd);
			}
		}
	}

	public void addAllPEDs(Set<PotentialEdgeDependency> peds) {
		for (PotentialEdgeDependency ped : peds) {
			if (!exclude(ped)) {
				potentialEdgeDependencies.add(ped);
			}
		}
	}

	public void addAllPADs(Set<PotentialAttributeDependency> pads) {
		for (PotentialAttributeDependency pad : pads) {
			if (!exclude(pad)) {
				potentialAttributeDependencies.add(pad);
			}
		}
	}

	public void add(PotentialRuleDependencies deps) {
		addAllPNDs(deps.getPotentialNodeDependencies());
		addAllPEDs(deps.getPotentialEdgeDependencies());
		addAllPADs(deps.getPotentialAttributeDependencies());
	}

	// FIXME (will be obsolete in future Henshin Versions)
	/************************************************************************
	 * WORKAROUND: Verhindern von Dependency-Zyklen, solange
	 * CriticalPair-Analysis zur Berechnung potentieller Abhängigkeiten noch
	 * nicht integriert ist.
	 ************************************************************************/

	private boolean exclude(PotentialDependency pd) {
		if (pd.getKind() == PotentialDependencyKind.DELETE_FORBID) {
			String sourceName = pd.getSourceRule().getExecuteModule().getName();
			String targetName = pd.getTargetRule().getExecuteModule().getName();
			if (sourceName.equals("MOVE_EReference_Ref_eStructuralFeatures_To_EClass")
					&& targetName.equals("DELETE_EReferenceInEClass")) {
				return true;
			}
			if (sourceName.equals("DELETE_EReferenceInEClass")
					&& targetName.equals("DELETE_EReferenceInEClass")) {
				return true;
			}
			if (sourceName.equals("DELETE_EReferenceInEClass")
					&& targetName.equals("MOVE_EReference_Ref_eStructuralFeatures_To_EClass")) {
				return true;
			}
			if (sourceName.equals("DELETE_EReferenceInEClass")
					&& targetName.equals("CHANGE_EReferenceType")) {
				return true;
			}
			if (sourceName.equals("CHANGE_EReferenceType")
					&& targetName.equals("DELETE_EReferenceInEClass")) {
				return true;
			}
			
		}

		return false;
	}

	/************************************************************************
	 * WORKAROUND ENDE
	 ************************************************************************/
}

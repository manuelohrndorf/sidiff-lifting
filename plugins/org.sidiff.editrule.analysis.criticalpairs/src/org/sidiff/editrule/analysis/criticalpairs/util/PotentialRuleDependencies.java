package org.sidiff.editrule.analysis.criticalpairs.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.sidiff.editrule.rulebase.PotentialAttributeDependency;
import org.sidiff.editrule.rulebase.PotentialDependency;
import org.sidiff.editrule.rulebase.PotentialDependencyKind;
import org.sidiff.editrule.rulebase.PotentialEdgeDependency;
import org.sidiff.editrule.rulebase.PotentialNodeDependency;

/**
 * Simple container class that holds different kinds of potential dependencies.
 * 
 */
// TODO: When we are sure that everything runs fine we can remove the
// "unmodifiableSet"-calls to get better performance
public class PotentialRuleDependencies {

	private Set<PotentialNodeDependency> potentialNodeDependencies;
	private Set<PotentialEdgeDependency> potentialEdgeDependencies;
	private Set<PotentialAttributeDependency> potentialAttributeDependencies;

	public Set<PotentialNodeDependency> getPotentialNodeDependencies() {
		if (potentialNodeDependencies == null) {
			potentialNodeDependencies = new HashSet<PotentialNodeDependency>();
		}

		return Collections.unmodifiableSet(potentialNodeDependencies);
	}

	public Set<PotentialEdgeDependency> getPotentialEdgeDependencies() {
		if (potentialEdgeDependencies == null) {
			potentialEdgeDependencies = new HashSet<PotentialEdgeDependency>();
		}

		return Collections.unmodifiableSet(potentialEdgeDependencies);
	}

	public Set<PotentialAttributeDependency> getPotentialAttributeDependencies() {
		if (potentialAttributeDependencies == null) {
			potentialAttributeDependencies = new HashSet<PotentialAttributeDependency>();
		}

		return Collections.unmodifiableSet(potentialAttributeDependencies);
	}

	public Set<PotentialDependency> getPotentialDependencies() {
		Set<PotentialDependency> potDeps = new HashSet<PotentialDependency>();
		potDeps.addAll(getPotentialNodeDependencies());
		potDeps.addAll(getPotentialEdgeDependencies());
		potDeps.addAll(getPotentialAttributeDependencies());

		return Collections.unmodifiableSet(potDeps);
	}

	public void addAllPNDs(Set<PotentialNodeDependency> pnds) {
		if (potentialNodeDependencies == null) {
			potentialNodeDependencies = new HashSet<PotentialNodeDependency>();
		}

		for (PotentialNodeDependency pnd : pnds) {
			if (!exclude(pnd)) {
				potentialNodeDependencies.add(pnd);
			}
		}
	}

	public void addAllPEDs(Set<PotentialEdgeDependency> peds) {
		if (potentialEdgeDependencies == null) {
			potentialEdgeDependencies = new HashSet<PotentialEdgeDependency>();
		}

		for (PotentialEdgeDependency ped : peds) {
			if (!exclude(ped)) {
				potentialEdgeDependencies.add(ped);
			}
		}
	}

	public void addAllPADs(Set<PotentialAttributeDependency> pads) {
		if (potentialAttributeDependencies == null) {
			potentialAttributeDependencies = new HashSet<PotentialAttributeDependency>();
		}

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
			if (pd.getSourceRule().getExecuteModule().getName().equals("MOVE_EReference_Ref_eStructuralFeatures_To_EClass")
					&& pd.getTargetRule().getExecuteModule().getName().equals("DELETE_EReferenceInEClass")) {
				return true;
			}
			if (pd.getSourceRule().getExecuteModule().getName().equals("DELETE_EReferenceInEClass")
					&& pd.getTargetRule().getExecuteModule().getName().equals("DELETE_EReferenceInEClass")) {
				return true;
			}
			if (pd.getSourceRule().getExecuteModule().getName().equals("DELETE_EReferenceInEClass")
					&& pd.getTargetRule().getExecuteModule().getName().equals("MOVE_EReference_Ref_eStructuralFeatures_To_EClass")) {
				return true;
			}
			if (pd.getSourceRule().getExecuteModule().getName().equals("DELETE_EReferenceInEClass")
					&& pd.getTargetRule().getExecuteModule().getName().equals("CHANGE_EReferenceType")) {
				return true;
			}
			if (pd.getSourceRule().getExecuteModule().getName().equals("CHANGE_EReferenceType")
					&& pd.getTargetRule().getExecuteModule().getName().equals("DELETE_EReferenceInEClass")) {
				return true;
			}
			
		}

		return false;
	}

	/************************************************************************
	 * WORKAROUND ENDE
	 ************************************************************************/
}

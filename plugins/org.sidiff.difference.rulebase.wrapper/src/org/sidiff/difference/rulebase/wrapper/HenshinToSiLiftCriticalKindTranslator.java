package org.sidiff.difference.rulebase.wrapper;

import org.eclipse.emf.henshin.cpa.result.Conflict;
import org.eclipse.emf.henshin.cpa.result.ConflictKind;
import org.eclipse.emf.henshin.cpa.result.CriticalPair;
import org.eclipse.emf.henshin.cpa.result.Dependency;
import org.eclipse.emf.henshin.cpa.result.DependencyKind;
import org.sidiff.difference.rulebase.PotentialDependencyKind;

/**
 * This translation is based on the publication:
 * 
 * Kehrer, Timo, Udo Kelter, and Gabriele Taentzer. "Consistency-preserving edit scripts in model versioning." 
 * Automated Software Engineering (ASE), 2013 IEEE/ACM 28th International Conference on. IEEE, 2013.
 *  
 * @author Kristopher Born
 *
 */
public class HenshinToSiLiftCriticalKindTranslator {

	public PotentialDependencyKind translate(CriticalPair criticalPair) {
		if(criticalPair instanceof Dependency)
			return translateDependencies((Dependency)criticalPair);
		if(criticalPair instanceof Conflict)
			return translateConflict((Conflict)criticalPair);
		return null;
	}

	private PotentialDependencyKind translateConflict(Conflict conflict) {
		ConflictKind conflictKind = conflict.getConflictKind();
		if(conflictKind == ConflictKind.DELETE_USE_CONFLICT)
			return PotentialDependencyKind.USE_DELETE;
		if(conflictKind == ConflictKind.CHANGE_USE_ATTR_CONFLICT)
			return PotentialDependencyKind.USE_CHANGE;
		if(conflictKind == ConflictKind.PRODUCE_FORBID_CONFLICT)
			return PotentialDependencyKind.FORBID_CREATE;
		if(conflictKind == ConflictKind.DELETE_USE_CONFLICT)
			return PotentialDependencyKind.FORBID_CHANGE;
		return null;
	}

	private PotentialDependencyKind translateDependencies(Dependency dependency) {
		DependencyKind dependencyKind = dependency.getDependencyKind();
		if(dependencyKind == DependencyKind.PRODUCE_USE_DEPENDENCY)
			return PotentialDependencyKind.CREATE_USE;
		if(dependencyKind == DependencyKind.CHANGE_USE_ATTR_DEPENDENCY)
			return PotentialDependencyKind.CHANGE_USE;
		if(dependencyKind == DependencyKind.DELETE_FORBID_DEPENDENCY)
			return PotentialDependencyKind.DELETE_FORBID;
		if(dependencyKind == DependencyKind.CHANGE_FORBID_ATTR_DEPENDENCY)
			return PotentialDependencyKind.CHANGE_FORBID;
		return null;
	}
}

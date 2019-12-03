package org.sidiff.difference.asymmetric.dependencies.real;

import static org.sidiff.difference.asymmetric.util.AsymmetricDifferenceUtil.getOperationInvocationOfSCS;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.emf.access.Link;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricFactory;
import org.sidiff.difference.asymmetric.AttributeDependency;
import org.sidiff.difference.asymmetric.Dependency;
import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.EdgeDependency;
import org.sidiff.difference.asymmetric.NodeDependency;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.dependencies.real.exceptions.DependencyCycleException;
import org.sidiff.difference.asymmetric.util.AsymmetricDifferenceUtil;
import org.sidiff.difference.asymmetric.util.CycleChecker;
import org.sidiff.difference.lifting.recognitionengine.IEditRuleMatch;
import org.sidiff.difference.rulebase.view.ILiftingRuleBase;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.SemanticChangeSet;
import org.sidiff.editrule.analysis.criticalpairs.InterRuleBasePotentialDependencyAnalyzer;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.PotentialAttributeDependency;
import org.sidiff.editrule.rulebase.PotentialDependency;
import org.sidiff.editrule.rulebase.PotentialEdgeDependency;
import org.sidiff.editrule.rulebase.PotentialNodeDependency;

public abstract class DependencyAnalyzer {

	/**
	 * The asymmetric difference
	 */
	private AsymmetricDifference asymmetricDiff;

	/**
	 * The {@link ILiftingRuleBase} used by {@link #recognitionEngine}/{@link #asymmetricDiff}
	 */
	private Set<ILiftingRuleBase> ruleBases;

	/**
	 * 
	 */
	private Map<EditRule, Set<SemanticChangeSet>> editRule2SCS;

	/**
	 * Analysis the dependencies between different rulebases
	 */
	private InterRuleBasePotentialDependencyAnalyzer crossOverPotDeps;

	public DependencyAnalyzer(AsymmetricDifference asymmetricDiff) {
		this.asymmetricDiff = Objects.requireNonNull(asymmetricDiff, "asymmetricDiff is null");
	}

	protected abstract Set<ILiftingRuleBase> initializeRuleBases();
	protected abstract Map<EditRule, Set<SemanticChangeSet>> initializeEditRule2SCS();

	public void analyze() {
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "-------------------- ANALYZE DEPENDENCIES ------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		ruleBases = new HashSet<>(initializeRuleBases());
		editRule2SCS = new HashMap<>(initializeEditRule2SCS());

		// Initialize RuleBase cross-over potential dependency analyzer
		if (ruleBases.size() > 1) {
			crossOverPotDeps = new InterRuleBasePotentialDependencyAnalyzer(ruleBases);
		} else {
			crossOverPotDeps = null;
		}

		// Run through all types of edit rules contained in the lifted difference:
		for (Map.Entry<EditRule, Set<SemanticChangeSet>> entry : editRule2SCS.entrySet()) {
			EditRule erSrc = entry.getKey();

			// Run through all potential dependencies of the edit rule type:
			for (PotentialDependency potDep : getPotentialDependencies(erSrc)) {
				assert (erSrc == potDep.getSourceRule()) : "erSrc != potDep.sourceRule";

				// Run through all occurring SCS of the actual edit rule type:
				for (SemanticChangeSet scsSrc : entry.getValue()) {
					// Test potential target dependencies for real dependency:
					EditRule erTgt = potDep.getTargetRule();
					Set<SemanticChangeSet> scsTgts = editRule2SCS.get(erTgt);

					// No target(s) found for potential dependency.
					if (scsTgts == null) {
						continue;
					}

					for (SemanticChangeSet scsTgt : scsTgts) {
						// Skip target SCS if same as source.
						// Otherwise some rules that use <<forbid>> may create a dependency cycle with themselves.
						if(scsSrc == scsTgt) {
							continue;
						}

						// Get matches of potentially depending edit rules:
						IEditRuleMatch erSrcMatch = getEditRuleMatch(scsSrc);
						IEditRuleMatch erTgtMatch = getEditRuleMatch(scsTgt);

						Dependency dependency;
						if (potDep instanceof PotentialNodeDependency) {
							dependency = intersects(erSrcMatch, erTgtMatch, (PotentialNodeDependency) potDep)
											.map(DependencyAnalyzer::createNodeDependency).orElse(null);
						} else if (potDep instanceof PotentialEdgeDependency) {
							dependency = intersects(erSrcMatch, erTgtMatch, (PotentialEdgeDependency) potDep)
											.map(DependencyAnalyzer::createEdgeDependency).orElse(null);
						} else if (potDep instanceof PotentialAttributeDependency) {
							dependency = intersects(erSrcMatch, erTgtMatch, (PotentialAttributeDependency)potDep, scsTgt)
											.map(i -> createAttributeDependency((PotentialAttributeDependency)potDep, i)).orElse(null);
						} else {
							throw new AssertionError();
						}
						if(dependency == null) {
							break;
						}

						dependency.setKind(AsymmetricDifferenceUtil.getDependencyKind(potDep.getKind()));

						OperationInvocation src = getOperationInvocationOfSCS(asymmetricDiff, scsSrc);
						OperationInvocation tgt = getOperationInvocationOfSCS(asymmetricDiff, scsTgt);

						DependencyContainer depContainer =
							asymmetricDiff.getDepContainers().stream()
								.filter(c -> c.getSource() == src && c.getTarget() == tgt)
								.findFirst()
								.orElseGet(() -> {
									DependencyContainer c = AsymmetricFactory.eINSTANCE.createDependencyContainer();
									// Real dependency found: scsSrc --> scsTgt
									c.setSource(src);
									c.setTarget(tgt);
									asymmetricDiff.getDepContainers().add(c);
									return c;
								});
						depContainer.getDependencies().add(dependency);

						LogUtil.log(LogEvent.DEBUG, "Potential Dependency:\n" + potDep + "\nActual Dependency:\n"
								+ erSrc.getExecuteModule().getName() + " -> " + erTgt.getExecuteModule().getName());
					}
				}
			}
		}

		List<EditRule> cycle = CycleChecker.check(asymmetricDiff.getOperationInvocations());
		if(!cycle.isEmpty()) {
			throw new DependencyCycleException(cycle);
		}
	}

	private static Dependency createNodeDependency(EObject intersection) {
		NodeDependency dependency = AsymmetricFactory.eINSTANCE.createNodeDependency();
		dependency.setObject(intersection);
		return dependency;
	}

	private static Dependency createEdgeDependency(Link intersection) {
		EdgeDependency dependency = AsymmetricFactory.eINSTANCE.createEdgeDependency();
		dependency.setSrcObject(intersection.getSrc());
		dependency.setTgtObject(intersection.getTgt());
		dependency.setType(intersection.getType());
		return dependency;
	}

	private static Dependency createAttributeDependency(PotentialAttributeDependency potDep, EObject intersection) {
		AttributeDependency dependency = AsymmetricFactory.eINSTANCE.createAttributeDependency();
		dependency.setObject(intersection);
		dependency.setType(potDep.getSourceAttribute().getType());
		return dependency;
	}

	protected abstract IEditRuleMatch getEditRuleMatch(SemanticChangeSet scs);
	
	private Set<PotentialDependency> getPotentialDependencies(EditRule erSrc) {
		// Rule base internal potential dependencies
		Set<PotentialDependency> potDeps = new HashSet<>();
		for (ILiftingRuleBase rb : ruleBases) {
			potDeps.addAll(rb.getPotentialDependencies(erSrc));
		}

		// Rule base cross over potential dependencies
		// (recognitionEngine.getUsedRulebases().size() > 1)
		if (crossOverPotDeps != null && crossOverPotDeps.isNecessary()) {
			potDeps.addAll(crossOverPotDeps.getPotentialDependencies(erSrc));
		}

		return potDeps;
	}


	/**
	 * Intersection test for potential node dependencies.
	 * 
	 * @param erSrcMatch
	 * @param erTgtMatch
	 * @param pnd
	 * @return An object, which is finally an {@link EObject}. 
	 * If the intersection is empty, null will be returned.
	 */
	private Optional<EObject> intersects(IEditRuleMatch erSrcMatch, IEditRuleMatch erTgtMatch, PotentialNodeDependency pnd) {
		switch(pnd.getKind()) {
			case USE_DELETE: return intersectsUseDelete(erSrcMatch, erTgtMatch, pnd);
			case CREATE_USE: return intersectsCreateUse(erSrcMatch, erTgtMatch, pnd);
			case DELETE_FORBID: return intersectsDeleteForbid(erSrcMatch, erTgtMatch, pnd);
			case FORBID_CREATE: return intersectsForbidCreate(pnd);
			default: throw new AssertionError("Unknown dependency kind: " + pnd.getKind());
		}
	}

	private Optional<EObject> intersectsUseDelete(IEditRuleMatch erSrcMatch, IEditRuleMatch erTgtMatch,
			PotentialNodeDependency pnd) {
		// check A intersection
		Set<EObject> srcOccurence = erSrcMatch.getOccurenceA(pnd.getSourceNode());
		Set<EObject> tgtOccurence = erTgtMatch.getOccurenceA(pnd.getTargetNode());
		if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
			Set<EObject> result = new HashSet<>(srcOccurence);
			result.retainAll(tgtOccurence);
			assert result.size() == 1 : "PND-UseDelete: the intersection has " + result.size() + "elements (should only have one).";
			return result.stream().findFirst();
		}
		return Optional.empty();
	}

	private Optional<EObject> intersectsCreateUse(IEditRuleMatch erSrcMatch, IEditRuleMatch erTgtMatch,
			PotentialNodeDependency pnd) {
		// check B intersection
		Set<EObject> srcOccurence = erSrcMatch.getOccurenceB(pnd.getSourceNode());
		Set<EObject> tgtOccurence = erTgtMatch.getOccurenceB(pnd.getTargetNode());
		if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
			Set<EObject> result = new HashSet<>(srcOccurence);
			result.retainAll(tgtOccurence);
			assert result.size() == 1 : "PND-CreateUse: the intersection has " + result.size() + "elements (should only have one).";
			return result.stream().findFirst();
		}
		return Optional.empty();
	}

	private Optional<EObject> intersectsDeleteForbid(IEditRuleMatch erSrcMatch, IEditRuleMatch erTgtMatch,
			PotentialNodeDependency pnd) {
		// check A intersection
		Set<EObject> srcOccurence = erSrcMatch.getForbidNodeOccurenceA(pnd.getSourceNode());
		Set<EObject> tgtOccurence = erTgtMatch.getOccurenceA(pnd.getTargetNode());
		if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
			Set<EObject> result = new HashSet<>(srcOccurence);
			result.retainAll(tgtOccurence);
			assert result.size() == 1 : "PND-DeleteForbid: the intersection has " + result.size() + "elements (should only have one).";
			return result.stream().findFirst();
		}
		return Optional.empty();
	}

	private Optional<EObject> intersectsForbidCreate(PotentialNodeDependency pnd) {
		// Due to our conceptual design, we will never detect operations
		// leading to a ForbidCreate dependency
		LogUtil.log(LogEvent.WARNING, "Ignored potential ForbidCreate-NodeDependency: " + pnd);
		return Optional.empty();
	}


	/**
	 * Intersection test for potential edge dependencies.
	 * 
	 * @param erSrcMatch
	 * @param erTgtMatch
	 * @param ped
	 * @return An object, which is finally an {@link Link}. 
	 * If the intersection is empty, null will be returned.
	 */
	private Optional<Link> intersects(IEditRuleMatch erSrcMatch, IEditRuleMatch erTgtMatch, PotentialEdgeDependency ped) {
		switch(ped.getKind()) {
			case USE_DELETE: return intersectsUseDelete(ped);
			case CREATE_USE: return intersectsCreateUse(erSrcMatch, erTgtMatch, ped);
			case DELETE_FORBID: return intersectsDeleteForbid(erSrcMatch, erTgtMatch, ped);
			case FORBID_CREATE: return intersectsForbidCreate(ped);
			default: throw new AssertionError("Unknown dependency kind: " + ped.getKind());
		}
	}

	private Optional<Link> intersectsUseDelete(PotentialEdgeDependency ped) {
		// // check A intersection
		// Set<Link> srcOccurence =
		// erSrcMatch.getOccurenceA(ped.getSourceEdge());
		// Set<Link> tgtOccurence =
		// erTgtMatch.getOccurenceA(ped.getTargetEdge());
		// if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
		// return true;
		// }
		//
		// TODO
		// Due to our conceptual design, we will never detect operations
		// leading to a UseDelete-EdgeDependency
		LogUtil.log(LogEvent.WARNING, "Ignored potential UseDelete-EdgeDependency: " + ped);
		return Optional.empty();
	}

	private Optional<Link> intersectsCreateUse(IEditRuleMatch erSrcMatch, IEditRuleMatch erTgtMatch,
			PotentialEdgeDependency ped) {
		// check B intersection
		Set<Link> srcOccurence = erSrcMatch.getOccurenceB(ped.getSourceEdge());
		Set<Link> tgtOccurence = erTgtMatch.getOccurenceB(ped.getTargetEdge());

		if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
			Set<Link> result = new HashSet<>(srcOccurence);
			result.retainAll(tgtOccurence);
			assert result.size() == 1 : "PED-CreateUse: the intersection has " + result.size() + "elements (should only have one).";
			return result.stream().findFirst();
		}
		return Optional.empty();
	}

	private Optional<Link> intersectsDeleteForbid(IEditRuleMatch erSrcMatch, IEditRuleMatch erTgtMatch,
			PotentialEdgeDependency ped) {
		// check A intersection
		Set<Link> srcOccurence = erSrcMatch.getForbidEdgeOccurenceA(ped.getSourceEdge());
		Set<Link> tgtOccurence = erTgtMatch.getOccurenceA(ped.getTargetEdge());
		if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
			Set<Link> result = new HashSet<>(srcOccurence);
			result.retainAll(tgtOccurence);
			assert result.size() == 1 : "PED-DeleteForbid: the intersection has " + result.size() + "elements (should only have one).";
			return result.stream().findFirst();
		}
		return Optional.empty();
	}

	private Optional<Link> intersectsForbidCreate(PotentialEdgeDependency ped) {
		// TODO
		// Due to our conceptual design, we will never detect operations
		// leading to a ForbidCreate-EdgeDependency
		LogUtil.log(LogEvent.WARNING, "Ignored potential ForbidCreate-Dependency: " + ped);
		return Optional.empty();
	}


	/**
	 * Intersection test for potential attribute dependencies.
	 * 
	 * @param erSrcMatch
	 * @param erTgtMatch
	 * @param pad
	 * @param scsSrc
	 * @return An object, which is finally an {@link EObject}.
	 * If the intersection is empty, null will be returned.
	 */
	private Optional<EObject> intersects(IEditRuleMatch erSrcMatch, IEditRuleMatch erTgtMatch,
			PotentialAttributeDependency pad, SemanticChangeSet scsTgt) {

		assert(pad.getSourceAttribute().getType() == pad.getTargetAttribute().getType());

		switch(pad.getKind()) {
			case CHANGE_USE: return intersectsChangeUse(erSrcMatch, erTgtMatch, pad, scsTgt);
			case USE_CHANGE: return intersectsUseChange(pad);
			case FORBID_CHANGE: return intersectsForbidChange(pad);
			case CHANGE_FORBID: return intersectsChangeForbid(erSrcMatch, erTgtMatch, pad);
			default: throw new AssertionError("Unknown dependency kind: " + pad.getKind());
		}
	}

	private Optional<EObject> intersectsChangeUse(IEditRuleMatch erSrcMatch, IEditRuleMatch erTgtMatch,
			PotentialAttributeDependency pad, SemanticChangeSet scsTgt) {
		// Bedingung 1: Objektschnittmenge nicht leer
		Set<EObject> srcOccurence = erSrcMatch.getOccurenceB(pad.getSourceNode());
		Set<EObject> tgtOccurence = erTgtMatch.getOccurenceB(pad.getTargetNode());
		for (EObject oB : srcOccurence) {
			if (tgtOccurence.contains(oB)) {
				// Bedingung 2: oB muss auch in A existieren, sonst hätte
				// AttributeValueChange stattgefunden haben können
				EObject oA = asymmetricDiff.getSymmetricDifference().getCorrespondingObjectInA(oB);
				if (oA == null) {
					continue;
				}

				// Bedingung 3: scsTgt muss AttrValueChange avc besitzen
				// mit avc.type = pad.type UND avc.objA = A.o UND
				// avc.objB = B.o
				for (Change change : scsTgt.getChanges()) {
					if (change instanceof AttributeValueChange) {
						AttributeValueChange avc = (AttributeValueChange) change;
						if (avc.getType() == pad.getTargetAttribute().getType() && avc.getObjA() == oA
								&& avc.getObjB() == oB) {

							// Bedingung 4: Attributwert von B.o muss
							// dem geforderten Literal der src EditRule
							// entsprechen (können wir aus
							// pad.sourceAttribute.value holen)
							if(oB.eGet(avc.getType()).toString().equals(pad.getSourceAttribute().getValue())) {
								Set<EObject> result = new HashSet<>(srcOccurence);
								result.retainAll(tgtOccurence);
								assert result.size() == 1 : "PED-ChangeUse: the intersection has "
										+ result.size() + "elements (should only have one).";
								return result.stream().findFirst();
							}
						}
					}
				}
			}
		}
		return Optional.empty();
	}

	private Optional<EObject> intersectsUseChange(PotentialAttributeDependency pad) {
		// TODO
		// Due to our conceptual design, we will never detect operations
		// leading to a UseChange-AttributeDependency
		LogUtil.log(LogEvent.WARNING, "Ignored potential UseChange-AttributeDependency: " + pad);
		return Optional.empty();
	}

	private Optional<EObject> intersectsForbidChange(PotentialAttributeDependency pad) {
		// TODO
		// Due to our conceptual design, we will never detect operations
		// leading to a ForbidChange-AttributeDependency
		LogUtil.log(LogEvent.WARNING, "Ignored potential ForbidChange-AttributeDependency: " + pad);
		return Optional.empty();
	}

	private Optional<EObject> intersectsChangeForbid(IEditRuleMatch erSrcMatch, IEditRuleMatch erTgtMatch,
			PotentialAttributeDependency pad) {
		// check A intersection
		Set<EObject> srcOccurence = erSrcMatch.getForbidNodeOccurenceA(pad.getSourceNode());
		Set<EObject> tgtOccurence = erTgtMatch.getOccurenceA(pad.getTargetNode());
		if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
			Set<EObject> result = new HashSet<>(srcOccurence);
			result.retainAll(tgtOccurence);
			assert result.size() == 1 : "PAD-ChangeForbid: the intersection has " + result.size() + "elements (should only have one).";
			return result.stream().findFirst();
		}
		return Optional.empty();
	}


	protected AsymmetricDifference getAsymmetricDiff() {
		return asymmetricDiff;
	}

	protected Set<ILiftingRuleBase> getRuleBases() {
		return Collections.unmodifiableSet(ruleBases);
	}

	protected Map<EditRule, Set<SemanticChangeSet>> getEditRule2SCS() {
		return Collections.unmodifiableMap(editRule2SCS);
	}
}

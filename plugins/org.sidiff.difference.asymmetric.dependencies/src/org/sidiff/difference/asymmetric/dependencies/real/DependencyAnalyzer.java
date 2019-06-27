package org.sidiff.difference.asymmetric.dependencies.real;

import static org.sidiff.difference.asymmetric.util.AsymmetricDifferenceUtil.getOperationInvocationOfSCS;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
import org.sidiff.difference.asymmetric.DependencyKind;
import org.sidiff.difference.asymmetric.EdgeDependency;
import org.sidiff.difference.asymmetric.NodeDependency;
import org.sidiff.difference.asymmetric.OperationInvocation;
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
import org.sidiff.editrule.rulebase.PotentialDependencyKind;
import org.sidiff.editrule.rulebase.PotentialEdgeDependency;
import org.sidiff.editrule.rulebase.PotentialNodeDependency;

public abstract class DependencyAnalyzer {

	/**
	 * The asymmetric difference
	 */
	protected AsymmetricDifference asymmetricDiff;
	
	/**
	 * The {@link ILiftingRuleBase} used by {@link #recognitionEngine}/{@link #asymmetricDiff}
	 */
	protected  Set<ILiftingRuleBase> ruleBases;
	
	/**
	 * 
	 */
	protected Map<EditRule, Set<SemanticChangeSet>> editRule2SCS;

	/**
	 * Analysis the dependencies between different rulebases
	 */
	InterRuleBasePotentialDependencyAnalyzer crossOverPotDeps;

	public DependencyAnalyzer(AsymmetricDifference asymmetricDiff) {
		this.asymmetricDiff = asymmetricDiff;
		this.ruleBases = new HashSet<ILiftingRuleBase>();
		this.editRule2SCS = new HashMap<EditRule, Set<SemanticChangeSet>>();
	}
	
	protected abstract void initialize();

	public void analyze() {
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "-------------------- ANALYZE DEPENDENCIES ------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		
		initialize();
		
		// Initialize RuleBase cross-over potential dependency analyzer
		if (ruleBases.size() > 1) {
			crossOverPotDeps = new InterRuleBasePotentialDependencyAnalyzer(ruleBases);
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

						// Get matches of potentially depending edit rules:
						IEditRuleMatch erSrcMatch = getEditRuleMatch(scsSrc);
						IEditRuleMatch erTgtMatch = getEditRuleMatch(scsTgt);
						OperationInvocation src = getOperationInvocationOfSCS(asymmetricDiff, scsSrc);
						OperationInvocation tgt = getOperationInvocationOfSCS(asymmetricDiff, scsTgt);
						
						Dependency dependency;
						if (potDep instanceof PotentialNodeDependency) {
							EObject intersection = intersects(erSrcMatch, erTgtMatch, (PotentialNodeDependency) potDep);
							dependency = createNodeDependency(intersection);
						} else if (potDep instanceof PotentialEdgeDependency) {
							Link intersection = intersects(erSrcMatch, erTgtMatch, (PotentialEdgeDependency) potDep);
							dependency = createEdgeDependency(intersection);
						} else if (potDep instanceof PotentialAttributeDependency) {
							EObject intersection = intersects(erSrcMatch, erTgtMatch, (PotentialAttributeDependency)potDep, scsTgt);
							dependency = createAttributeDependency((PotentialAttributeDependency)potDep, intersection);
						} else {
							throw new AssertionError();
						}

						dependency.setKind(getDependencyKind(potDep.getKind()));

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
		
		//-------------------------------------------------------------------------------------------------
		//TODO: Assertion: OperationInvocation-Dependencies zyklenfrei (Task #112) -> noch zu testen
		Collection<EditRule> cycle = new CycleChecker(asymmetricDiff.getOperationInvocations()).check();
		assert (cycle.isEmpty()) : "cycle between: " + cycle;
	}

	private Dependency createNodeDependency(EObject intersection) {
		NodeDependency dependency = AsymmetricFactory.eINSTANCE.createNodeDependency();
		dependency.setObject(intersection);
		return dependency;
	}

	private Dependency createEdgeDependency(Link intersection) {
		EdgeDependency dependency = AsymmetricFactory.eINSTANCE.createEdgeDependency();
		dependency.setSrcObject(intersection.getSrc());
		dependency.setTgtObject(intersection.getTgt());
		dependency.setType(intersection.getType());
		return dependency;
	}

	private Dependency createAttributeDependency(PotentialAttributeDependency potDep, EObject intersection) {
		AttributeDependency dependency = AsymmetricFactory.eINSTANCE.createAttributeDependency();
		dependency.setObject(intersection);
		dependency.setType(potDep.getSourceAttribute().getType());
		return dependency;
	}

	protected abstract IEditRuleMatch getEditRuleMatch(SemanticChangeSet scs);
	
	private Set<PotentialDependency> getPotentialDependencies(EditRule erSrc) {
		// Rule base internal potential dependencies
		Set<PotentialDependency> potDeps = new HashSet<PotentialDependency>();
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
	 * Simply maps PotentialDependecy.kind (from Rulebase model) onto
	 * Dependency.kind (from Difference model)
	 * 
	 * @param potDepKind
	 * @return
	 */
	private static DependencyKind getDependencyKind(PotentialDependencyKind potDepKind) {
		switch(potDepKind) {
			case CHANGE_FORBID: return DependencyKind.CHANGE_FORBID;
			case CHANGE_USE: return DependencyKind.CHANGE_USE;
			case CREATE_USE: return DependencyKind.CREATE_USE;
			case DELETE_FORBID: return DependencyKind.DELETE_FORBID;
			case FORBID_CHANGE: return DependencyKind.FORBID_CHANGE;
			case FORBID_CREATE: return DependencyKind.FORBID_CREATE;
			case USE_CHANGE: return DependencyKind.USE_CHANGE;
			case USE_DELETE: return DependencyKind.USE_DELETE;
		}
		throw new IllegalArgumentException("Invalid PotentialDependencyKind: " + potDepKind);
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
	private EObject intersects(IEditRuleMatch erSrcMatch, IEditRuleMatch erTgtMatch, PotentialNodeDependency pnd) {
		switch(pnd.getKind()) {
			case USE_DELETE: {
				// check A intersection
				Set<EObject> srcOccurence = erSrcMatch.getOccurenceA(pnd.getSourceNode());
				Set<EObject> tgtOccurence = erTgtMatch.getOccurenceA(pnd.getTargetNode());
				if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
					srcOccurence.retainAll(tgtOccurence);
					assert srcOccurence.size() == 1 : "PND-UseDelete: the intersection has " + srcOccurence.size() + "elements (should only have one).";
					return srcOccurence.iterator().next();
				}
				break;
			}
			case CREATE_USE: {
				// check B intersection
				Set<EObject> srcOccurence = erSrcMatch.getOccurenceB(pnd.getSourceNode());
				Set<EObject> tgtOccurence = erTgtMatch.getOccurenceB(pnd.getTargetNode());
				if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
					srcOccurence.retainAll(tgtOccurence);
					assert srcOccurence.size() == 1 : "PND-CreateUse: the intersection has " + srcOccurence.size() + "elements (should only have one).";
					return srcOccurence.iterator().next();
				}
				break;
			}
			case DELETE_FORBID: {
				// check A intersection
				Set<EObject> srcOccurence = erSrcMatch.getForbidNodeOccurenceA(pnd.getSourceNode());
				Set<EObject> tgtOccurence = erTgtMatch.getOccurenceA(pnd.getTargetNode());
				if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
					srcOccurence.retainAll(tgtOccurence);
					assert srcOccurence.size() == 1 : "PND-DeleteForbid: the intersection has " + srcOccurence.size() + "elements (should only have one).";
					return srcOccurence.iterator().next();
				}
				break;
			}
			case FORBID_CREATE: {
				// Due to our conceptual design, we will never detect operations
				// leading to a ForbidCreate dependency
				LogUtil.log(LogEvent.WARNING, "Ignored potential ForbidCreate-NodeDependency: " + pnd);
				break;
			}
			default:
				throw new AssertionError("Unknown dependency kind: " + pnd.getKind());
		}
		return null;
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
	private Link intersects(IEditRuleMatch erSrcMatch, IEditRuleMatch erTgtMatch, PotentialEdgeDependency ped) {
		switch(ped.getKind()) {
			case USE_DELETE: {
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
				break;
			}
			case CREATE_USE: {
				// check B intersection
				Set<Link> srcOccurence = erSrcMatch.getOccurenceB(ped.getSourceEdge());
				Set<Link> tgtOccurence = erTgtMatch.getOccurenceB(ped.getTargetEdge());

				if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
					srcOccurence.retainAll(tgtOccurence);
					assert srcOccurence.size() == 1 : "PED-CreateUse: the intersection has " + srcOccurence.size() + "elements (should only have one).";
					return srcOccurence.iterator().next();
				}
				break;
			}
			case DELETE_FORBID: {
				// check A intersection
				Set<Link> srcOccurence = erSrcMatch.getForbidEdgeOccurenceA(ped.getSourceEdge());
				Set<Link> tgtOccurence = erTgtMatch.getOccurenceA(ped.getTargetEdge());
				if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
					srcOccurence.retainAll(tgtOccurence);
					assert srcOccurence.size() == 1 : "PED-DeleteForbid: the intersection has " + srcOccurence.size() + "elements (should only have one).";
					return srcOccurence.iterator().next();
				}
				break;
			}
			case FORBID_CREATE: {
				// TODO
				// Due to our conceptual design, we will never detect operations
				// leading to a ForbidCreate-EdgeDependency
				LogUtil.log(LogEvent.WARNING, "Ignored potential ForbidCreate-Dependency: " + ped);
				break;
			}
			default:
				throw new AssertionError("Unknown dependency kind: " + ped.getKind());
		}
		return null;
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
	private EObject intersects(IEditRuleMatch erSrcMatch, IEditRuleMatch erTgtMatch, PotentialAttributeDependency pad,
			SemanticChangeSet scsTgt) {

		assert(pad.getSourceAttribute().getType() == pad.getTargetAttribute().getType());
		
		switch(pad.getKind()) {
			case CHANGE_USE: {
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
									if(oB.eGet(avc.getType()).toString().equals(pad.getSourceAttribute().getValue())){
										srcOccurence.retainAll(tgtOccurence);
										assert srcOccurence.size() == 1 : "PED-ChangeUse: the intersection has " + srcOccurence.size() + "elements (should only have one).";
										return srcOccurence.iterator().next();
									}
								}
							}
						}
					}
				}
				break;
			}
			case USE_CHANGE: {
				// TODO
				// Due to our conceptual design, we will never detect operations
				// leading to a UseChange-AttributeDependency
				LogUtil.log(LogEvent.WARNING, "Ignored potential UseChange-AttributeDependency: " + pad);
				break;
			}
			case FORBID_CHANGE: {
				// TODO
				// Due to our conceptual design, we will never detect operations
				// leading to a ForbidChange-AttributeDependency
				LogUtil.log(LogEvent.WARNING, "Ignored potential ForbidChange-AttributeDependency: " + pad);
				break;
			}
			case CHANGE_FORBID: {
				// check A intersection
				Set<EObject> srcOccurence = erSrcMatch.getForbidNodeOccurenceA(pad.getSourceNode());
				Set<EObject> tgtOccurence = erTgtMatch.getOccurenceA(pad.getTargetNode());
				if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
					srcOccurence.retainAll(tgtOccurence);
					assert srcOccurence.size() == 1 : "PAD-ChangeForbid: the intersection has " + srcOccurence.size() + "elements (should only have one).";
					return srcOccurence.iterator().next();
				}
				break;
			}
			default:
				throw new AssertionError("Unknown dependency kind: " + pad.getKind());
		}
		return null;
	}
}

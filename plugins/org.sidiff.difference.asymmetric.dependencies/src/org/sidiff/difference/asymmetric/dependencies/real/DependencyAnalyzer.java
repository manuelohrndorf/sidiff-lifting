package org.sidiff.difference.asymmetric.dependencies.real;

import static org.sidiff.difference.asymmetric.util.AsymmetricDifferenceUtil.getOperationInvocationOfSCS;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
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
import org.sidiff.difference.asymmetric.dependencies.potential.CrossOverPotentialDependencyAnalyzer;
import org.sidiff.difference.asymmetric.util.CycleChecker;
import org.sidiff.difference.lifting.recognitionengine.matching.EngineBasedEditRuleMatch;
import org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionEngine;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.PotentialAttributeDependency;
import org.sidiff.difference.rulebase.PotentialDependency;
import org.sidiff.difference.rulebase.PotentialDependencyKind;
import org.sidiff.difference.rulebase.PotentialEdgeDependency;
import org.sidiff.difference.rulebase.PotentialNodeDependency;
import org.sidiff.difference.rulebase.extension.IRuleBase;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.SemanticChangeSet;

public class DependencyAnalyzer {

	/**
	 * The RecognitionEngine instance that was used to semantically lift a
	 * difference
	 */
	private RecognitionEngine recognitionEngine;

	/**
	 * The asymmetric difference
	 */
	private AsymmetricDifference asymmetricDiff;

	/**
	 * Analysis the dependencies between different rulebases
	 */
	CrossOverPotentialDependencyAnalyzer crossOverPotDeps;

	/**
	 * Creates a {@link DependencyAnalyzer}
	 * 
	 * @param recognitionEngine
	 *            The RecognitionEngine instance that was used to semantically
	 *            lift a difference
	 */
	public DependencyAnalyzer(RecognitionEngine recognitionEngine, AsymmetricDifference asymmetricDiff) {
		this.recognitionEngine = recognitionEngine;
		this.asymmetricDiff = asymmetricDiff;
	}

	public void analyze() {
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "-------------------- ANALYZE DEPENDENCIES ------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		// Initialize RuleBase cross-over potential dependency analyzer
		if (recognitionEngine.getUsedRulebases().size() > 1) {
			crossOverPotDeps = new CrossOverPotentialDependencyAnalyzer(recognitionEngine);
		}

		// Map edit rule types to occurring SCS.
		Map<EditRule, Set<SemanticChangeSet>> editRule2SCS = recognitionEngine.getEditRule2SCS();

		// Run through all types of edit rules contained in the lifted
		// difference:
		for (EditRule erSrc : editRule2SCS.keySet()) {

			// Run through all potential dependencies of the edit rule type:

			// All potential dependencies
			Set<PotentialDependency> potDeps = getPotentialDependencies(erSrc);

			for (PotentialDependency potDep : potDeps) {

				assert (erSrc == potDep.getSourceRule()) : "erSrc != potDep.sourceRule";

				// Run through all occurring SCS of the actual edit rule type:
				for (SemanticChangeSet scsSrc : editRule2SCS.get(erSrc)) {

					// Test potential target dependencies for real dependency:
					EditRule erTgt = potDep.getTargetRule();
					Set<SemanticChangeSet> scsTgts = editRule2SCS.get(erTgt);

					// No target(s) found for potential dependency.
					if (scsTgts == null)
						continue;

					for (SemanticChangeSet scsTgt : scsTgts) {

						// Get matches of potentially depending edit rules:
						EngineBasedEditRuleMatch erSrcMatch = recognitionEngine.getEditRuleMatch(scsSrc);
						EngineBasedEditRuleMatch erTgtMatch = recognitionEngine.getEditRuleMatch(scsTgt);
						int kind = 0;
						Object intersection = null;
						if (potDep instanceof PotentialNodeDependency) {
							intersection = intersects(erSrcMatch, erTgtMatch, (PotentialNodeDependency) potDep);
							kind = 1;
						}
						if (potDep instanceof PotentialEdgeDependency) {
							intersection = intersects(erSrcMatch, erTgtMatch, (PotentialEdgeDependency) potDep);
							kind = 2;
						}
						if (potDep instanceof PotentialAttributeDependency) {
							intersection = intersects(erSrcMatch, erTgtMatch, (PotentialAttributeDependency) potDep, scsTgt);
							kind = 3;
						}
						
						if (intersection != null) {
							
							OperationInvocation src = getOperationInvocationOfSCS(asymmetricDiff, scsSrc);
							OperationInvocation tgt = getOperationInvocationOfSCS(asymmetricDiff, scsTgt);
							
							DependencyContainer depContainer = null;
							Dependency dependency = null;
							
							switch(kind){
							case 1: //NodeDependency
								dependency = AsymmetricFactory.eINSTANCE.createNodeDependency();
								((NodeDependency)dependency).setObject((EObject)intersection);
								break;
							case 2: //EdgeDependency
								dependency = AsymmetricFactory.eINSTANCE.createEdgeDependency();
								((EdgeDependency)dependency).setSrcObject(((Link)intersection).getSrc());
								((EdgeDependency)dependency).setTgtObject(((Link)intersection).getTgt());
								((EdgeDependency)dependency).setType(((Link)intersection).getType());
								break;
							case 3: //AttributeDepenency
								dependency = AsymmetricFactory.eINSTANCE.createAttributeDependency();
								((AttributeDependency)dependency).setObject((EObject)intersection);
								for(EAttribute e : ((EObject)intersection).eClass().getEAllAttributes()){
									if(e.getName().equals("name")){
										((AttributeDependency)dependency).setType(((EAttribute)((EObject)intersection).eGet(e)));
										break;
									}
								}
								break;
							default:
								break;
							}
							
							dependency.setKind(getDependencyKind(potDep));
							
							for(DependencyContainer c : asymmetricDiff.getDepContainers()){
								if(c.getSource().equals(src) && c.getTarget().equals(tgt)){
									depContainer = c;
									break;
								}
							}
							
							if(depContainer == null){
								depContainer = AsymmetricFactory.eINSTANCE.createDependencyContainer();
								// Real dependency found: scsSrc --> scsTgt
								depContainer.setSource(src);
								depContainer.setTarget(tgt);
							}
							
							depContainer.getDependencies().add(dependency);
							

							asymmetricDiff.getDepContainers().add(depContainer);

							LogUtil.log(LogEvent.DEBUG, "Potential Dependency:\n" + potDep + "\nActual Dependency:\n"
									+ erSrc.getExecuteModule().getName() + " -> " + erTgt.getExecuteModule().getName());
						}
					}
				}
			}
		}
		
		//-------------------------------------------------------------------------------------------------
		//TODO: Assertion: OperationInvocation-Dependencies zyklenfrei (Task #112) -> noch zu testen
		Collection<EditRule> cycle = new CycleChecker(asymmetricDiff.getOperationInvocations()).check();
		assert (cycle.isEmpty()) : "cycle between: " + cycle;
	}

	private Set<PotentialDependency> getPotentialDependencies(EditRule erSrc) {
		// Rule base internal potential dependencies
		Set<PotentialDependency> potDeps = new HashSet<PotentialDependency>();
		for (IRuleBase rb : recognitionEngine.getUsedRulebases()) {
			potDeps.addAll(rb.getPotentialDependencies(erSrc));
		}

		// Rule base cross over potential dependencies
		// (recognitionEngine.getUsedRulebases().size() > 1)
		if ((crossOverPotDeps != null) && (crossOverPotDeps.isNecessary())) {
			potDeps.addAll(crossOverPotDeps.getPotentialDependencies(erSrc));
		}

		return potDeps;
	}

	/**
	 * Simply maps PotentialDependecy.kind (from Rulebase model) onto
	 * Dependency.kind (from Difference model)
	 * 
	 * @param potDep
	 * @return
	 */
	private DependencyKind getDependencyKind(PotentialDependency potDep) {
		if (potDep.getKind().equals(PotentialDependencyKind.CREATE_USE)) {
			return DependencyKind.CREATE_USE;
		} else if (potDep.getKind().equals(PotentialDependencyKind.USE_DELETE)) {
			return DependencyKind.USE_DELETE;
		} else if (potDep.getKind().equals(PotentialDependencyKind.FORBID_CREATE)) {
			return DependencyKind.FORBID_CREATE;
		} else if (potDep.getKind().equals(PotentialDependencyKind.DELETE_FORBID)) {
			return DependencyKind.DELETE_FORBID;
		} else if (potDep.getKind().equals(PotentialDependencyKind.CHANGE_USE)) {
			return DependencyKind.CHANGE_USE;
		} else if (potDep.getKind().equals(PotentialDependencyKind.USE_CHANGE)) {
			return DependencyKind.USE_CHANGE;
		} else if (potDep.getKind().equals(PotentialDependencyKind.CHANGE_FORBID)) {
			return DependencyKind.CHANGE_FORBID;
		} else if (potDep.getKind().equals(PotentialDependencyKind.FORBID_CHANGE)) {
			return DependencyKind.FORBID_CHANGE;
		}

		// we should never get here
		assert (false) : "No valid dependency kind found!";
		return null;
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
	private Object intersects(EngineBasedEditRuleMatch erSrcMatch, EngineBasedEditRuleMatch erTgtMatch, PotentialNodeDependency pnd) {
		if (pnd.getKind() == PotentialDependencyKind.USE_DELETE) {
			// check A intersection
			Set<EObject> srcOccurence = erSrcMatch.getOccurenceA(pnd.getSourceNode());
			Set<EObject> tgtOccurence = erTgtMatch.getOccurenceA(pnd.getTargetNode());
			if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
				srcOccurence.retainAll(tgtOccurence);
				assert srcOccurence.size() == 1 : "PND-UseDelete: the intersection has " + srcOccurence.size() + "elements (should only have one).";
				return srcOccurence.iterator().next();
			}
		} else if (pnd.getKind() == PotentialDependencyKind.CREATE_USE) {
			// check B intersection
			Set<EObject> srcOccurence = erSrcMatch.getOccurenceB(pnd.getSourceNode());
			Set<EObject> tgtOccurence = erTgtMatch.getOccurenceB(pnd.getTargetNode());
			if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
				srcOccurence.retainAll(tgtOccurence);
				assert srcOccurence.size() == 1 : "PND-CreateUse: the intersection has " + srcOccurence.size() + "elements (should only have one).";
				return srcOccurence.iterator().next();
			}
		} else if (pnd.getKind() == PotentialDependencyKind.DELETE_FORBID) {
			// check A intersection
			Set<EObject> srcOccurence = erSrcMatch.getForbidNodeOccurenceA(pnd.getSourceNode());
			Set<EObject> tgtOccurence = erTgtMatch.getOccurenceA(pnd.getTargetNode());
			if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
				srcOccurence.retainAll(tgtOccurence);
				assert srcOccurence.size() == 1 : "PND-DeleteForbid: the intersection has " + srcOccurence.size() + "elements (should only have one).";
				return srcOccurence.iterator().next();
			}
		} else if (pnd.getKind() == PotentialDependencyKind.FORBID_CREATE) {
			// Due to our conceptual design, we will never detect operations
			// leading to a ForbidCreate dependency
			LogUtil.log(LogEvent.WARNING, "Ignored potential ForbidCreate-NodeDependency: " + pnd);
		} else {
			assert (false) : "Unknown dependency kind: " + pnd.getKind().getLiteral();
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
	private Object intersects(EngineBasedEditRuleMatch erSrcMatch, EngineBasedEditRuleMatch erTgtMatch, PotentialEdgeDependency ped) {
		if (ped.getKind() == PotentialDependencyKind.USE_DELETE) {
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
		} else if (ped.getKind() == PotentialDependencyKind.CREATE_USE) {
			// check B intersection
			Set<Link> srcOccurence = erSrcMatch.getOccurenceB(ped.getSourceEdge());
			Set<Link> tgtOccurence = erTgtMatch.getOccurenceB(ped.getTargetEdge());

			if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
				srcOccurence.retainAll(tgtOccurence);
				assert srcOccurence.size() == 1 : "PED-CreateUse: the intersection has " + srcOccurence.size() + "elements (should only have one).";
				return srcOccurence.iterator().next();
			}
		} else if (ped.getKind() == PotentialDependencyKind.DELETE_FORBID) {
			// check A intersection
			Set<Link> srcOccurence = erSrcMatch.getForbidEdgeOccurenceA(ped.getSourceEdge());
			Set<Link> tgtOccurence = erTgtMatch.getOccurenceA(ped.getTargetEdge());
			if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
				srcOccurence.retainAll(tgtOccurence);
				assert srcOccurence.size() == 1 : "PED-DeleteForbid: the intersection has " + srcOccurence.size() + "elements (should only have one).";
				return srcOccurence.iterator().next();
			}
		} else if (ped.getKind() == PotentialDependencyKind.FORBID_CREATE) {
			// TODO
			// Due to our conceptual design, we will never detect operations
			// leading to a ForbidCreate-EdgeDependency
			LogUtil.log(LogEvent.WARNING, "Ignored potential ForbidCreate-Dependency: " + ped);
		} else {
			assert (false) : "Unknown dependency kind: " + ped.getKind().getLiteral();
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
	private Object intersects(EngineBasedEditRuleMatch erSrcMatch, EngineBasedEditRuleMatch erTgtMatch, PotentialAttributeDependency pad,
			SemanticChangeSet scsTgt) {

		if (pad.getKind() == PotentialDependencyKind.CHANGE_USE) {

			// Bedingung 1: Objektschnittmenge nicht leer
			Set<EObject> srcOccurence = erSrcMatch.getOccurenceB(pad.getSourceNode());
			Set<EObject> tgtOccurence = erTgtMatch.getOccurenceB(pad.getTargetNode());
			for (EObject oB : srcOccurence) {
				if (tgtOccurence.contains(oB)) {

					// Bedingung 2: oB muss auch in A existieren, sonst hätte
					// AttributeValueChange stattgefunden haben können
					EObject oA = asymmetricDiff.getSymmetricDifference().getCorrespondingObjectInA(oB);
					if (oA != null) {

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
			}

		} else if (pad.getKind() == PotentialDependencyKind.USE_CHANGE) {
			// TODO
			// Due to our conceptual design, we will never detect operations
			// leading to a UseChange-AttributeDependency
			LogUtil.log(LogEvent.WARNING, "Ignored potential UseChange-AttributeDependency: " + pad);
		} else if (pad.getKind() == PotentialDependencyKind.FORBID_CHANGE) {
			// TODO
			// Due to our conceptual design, we will never detect operations
			// leading to a ForbidChange-AttributeDependency
			LogUtil.log(LogEvent.WARNING, "Ignored potential ForbidChange-AttributeDependency: " + pad);
		} else if (pad.getKind() == PotentialDependencyKind.CHANGE_FORBID) {
			// check A intersection
			Set<EObject> srcOccurence = erSrcMatch.getForbidNodeOccurenceA(pad.getSourceNode());
			Set<EObject> tgtOccurence = erTgtMatch.getOccurenceA(pad.getTargetNode());
			if (!Collections.disjoint(srcOccurence, tgtOccurence)) {
				srcOccurence.retainAll(tgtOccurence);
				assert srcOccurence.size() == 1 : "PAD-ChangeForbid: the intersection has " + srcOccurence.size() + "elements (should only have one).";
				return srcOccurence.iterator().next();
			}			
		} else {
			assert (false) : "Unknown dependency kind: " + pad.getKind().getLiteral();
		}

		return null;
	}
}

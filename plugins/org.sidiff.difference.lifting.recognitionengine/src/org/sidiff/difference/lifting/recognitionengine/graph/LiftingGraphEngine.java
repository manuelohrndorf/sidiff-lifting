package org.sidiff.difference.lifting.recognitionengine.graph;

import java.util.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.info.RuleInfo;
import org.eclipse.emf.henshin.interpreter.matching.constraints.*;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.henshin.SelfCleaningEngineImpl;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.SymmetricPackage;
import org.sidiff.matching.model.Correspondence;
import org.sidiff.matching.model.MatchingModelPackage;

public class LiftingGraphEngine extends SelfCleaningEngineImpl {

	private static final SymmetricPackage SYMMETRIC_PACKAGE = SymmetricPackage.eINSTANCE;
	private static final MatchingModelPackage MATCHING_PACKAGE = MatchingModelPackage.eINSTANCE;

	private LiftingGraphDomainMap domainMap;
	private LiftingGraphIndex changeIndex;
	
	/**
	 * Index and reverse check correspondences and meta-types from the model to the
	 * model difference during matching. Reverse checking of changes is optional
	 * ({@link LiftingGraphEngine#reverseCheckDifferenceChanges}).
	 */
	private boolean reverseCheckDifference = true;

	/**
	 * Index and reverse check correspondences and meta-types from the model to the
	 * model difference during matching.
	 * {@link LiftingGraphEngine#reverseCheckDifference} must be enabled.
	 */
	private boolean reverseCheckDifferenceChanges = false;

	public LiftingGraphEngine(LiftingGraphDomainMap domainMap, LiftingGraphIndex changeIndex) {
		this.domainMap = domainMap;
		this.changeIndex = changeIndex;
	}
	
	@Override
	public RuleInfo getRuleInfo(Rule rule) {
		
		// Need to create new rule info?
		if (!ruleInfos.containsKey(rule)) {
			RuleInfo newRuleInfo = super.getRuleInfo(rule);
			
			// Optimization: Extends variables with inverse cross-references checks.
			if (reverseCheckDifference) {
				createInverseCrossReferenceConstraints(newRuleInfo);
			}
			
			return newRuleInfo;
		} else {
			return super.getRuleInfo(rule);
		}
	}

	protected void createInverseCrossReferenceConstraints(RuleInfo ruleInfo) {
		Rule rule = ruleInfo.getRule();
		Map<Node, Variable> node2variable = ruleInfo.getVariableInfo().getNode2variable();
		
		// Incoming LHS (none containment) edges without opposite edges:
		for (Node node : rule.getLhs().getNodes()) {
			Variable var = node2variable.get(node);
			
			for (Edge edge : node.getIncoming()) {
				if (!edge.getType().isContainment() && edge.getType().getEOpposite() == null) {
					Variable target = node2variable.get(edge.getSource());
					ReferenceConstraint constraint = createInverseCrossReferenceConstraint(target, edge);
					
					if (constraint != null) {
						var.referenceConstraints.add(constraint);
					}
				}
			}
		}
	}

	public ReferenceConstraint createInverseCrossReferenceConstraint(Variable target, final Edge incoming) {
		
		// ModelA <-matchedA- Correspondence:
		if (incoming.getType() == MATCHING_PACKAGE.getCorrespondence_MatchedA()) {
			return new InverseCrossReferenceConstraint(target, incoming) {
				@Override
				public List<EObject> getInverseCrossReferenced(EObject source, Edge incoming) {
					Correspondence correspondence = domainMap.getDifference().getCorrespondenceOfModelA(source);
					if (correspondence != null) {
						List<EObject> modifiableSingleton = new ArrayList<>(1);
						modifiableSingleton.add(correspondence);
						return modifiableSingleton;
					}
					// Explicitly no match!
					return Collections.emptyList();
				}
			};
		}

		// ModelB <-matchedB- Correspondence:
		else if (incoming.getType() == MATCHING_PACKAGE.getCorrespondence_MatchedB()) {
			return new InverseCrossReferenceConstraint(target, incoming) {
				@Override
				public List<EObject> getInverseCrossReferenced(EObject source, Edge incoming) {
					Correspondence correspondence =  domainMap.getDifference().getCorrespondenceOfModelB(source);
					if (correspondence != null) {
						List<EObject> modifiableSingleton = new ArrayList<>(1);
						modifiableSingleton.add(correspondence);
						return modifiableSingleton;
					}
					// Explicitly no match!
					return Collections.emptyList();
				}
			};
		}

		// EReference <-type- RemoveReference/AddReference/AttributeValueChange:
		else if (incoming.getType() == SYMMETRIC_PACKAGE.getRemoveReference_Type()
				|| incoming.getType() == SYMMETRIC_PACKAGE.getAddReference_Type()
				|| incoming.getType() == SYMMETRIC_PACKAGE.getAttributeValueChange_Type()) {

			InverseCrossReferenceConstraint constraint = new InverseCrossReferenceConstraint(target, incoming) {

				@Override
				public List<EObject> getInverseCrossReferenced(EObject source, Edge incoming) {
					return domainMap.getChangeDomain(incoming.getSource().getType(), source);
				}
			};

			return constraint;
		}

		// Optional -> normally the node sorting prevents this case -> indexing overhead!
		// ModelElement <-objX/src/tgt- AddObject/RemoveObject/RemoveReference/AddReference/AttributeValueChange:
		else if (reverseCheckDifferenceChanges && (incoming.getType() == SYMMETRIC_PACKAGE.getAddObject_Obj()
				|| incoming.getType() == SYMMETRIC_PACKAGE.getRemoveObject_Obj()
				|| incoming.getType() == SYMMETRIC_PACKAGE.getAddReference_Src()
				|| incoming.getType() == SYMMETRIC_PACKAGE.getAddReference_Tgt()
				|| incoming.getType() == SYMMETRIC_PACKAGE.getRemoveReference_Src()
				|| incoming.getType() == SYMMETRIC_PACKAGE.getRemoveReference_Tgt()
				|| incoming.getType() == SYMMETRIC_PACKAGE.getAttributeValueChange_ObjA()
				|| incoming.getType() == SYMMETRIC_PACKAGE.getAttributeValueChange_ObjB())) {

			assert Change.class.isAssignableFrom(incoming.getSource().getType().getInstanceClass());

			return new InverseCrossReferenceConstraint(target, incoming) {
				@Override
				public List<EObject> getInverseCrossReferenced(EObject source, Edge incoming) {
					@SuppressWarnings("unchecked")
					Iterator<? extends Change> changeIterator = changeIndex.getLocalChanges(
							source, incoming.getType(),
							(Class<? extends Change>) incoming.getSource().getType().getInstanceClass());

					List<EObject> localChanges = new ArrayList<>();
					while (changeIterator.hasNext()) {
						localChanges.add(changeIterator.next());
					}
					return localChanges;
				}
			};
		}

		return null;
	}

	public boolean isReverseCheckDifference() {
		return reverseCheckDifference;
	}

	public void setReverseCheckDifference(boolean reverseCheckDifference) {
		this.reverseCheckDifference = reverseCheckDifference;
	}

	public boolean isReverseCheckDifferenceChanges() {
		return reverseCheckDifferenceChanges;
	}

	public void setReverseCheckDifferenceChanges(boolean reverseCheckDifferenceChanges) {
		this.reverseCheckDifferenceChanges = reverseCheckDifferenceChanges;
	}

	public LiftingGraphDomainMap getDomainMap() {
		return domainMap;
	}

	public void setDomainMap(LiftingGraphDomainMap domainMap) {
		this.domainMap = domainMap;
	}

	public LiftingGraphIndex getChangeIndex() {
		return changeIndex;
	}

	public void setChangeIndex(LiftingGraphIndex changeIndex) {
		this.changeIndex = changeIndex;
	}
}

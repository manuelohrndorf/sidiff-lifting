package org.sidiff.difference.lifting.recognitionengine.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.matching.constraints.CrossReferenceConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;
import org.eclipse.emf.henshin.model.Edge;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.SymmetricPackage;
import org.sidiff.matching.model.Correspondence;
import org.sidiff.matching.model.MatchingModelPackage;

public class LiftingGraphEngine extends EngineImpl {

	private static final SymmetricPackage SYMMETRIC_PACKAGE = SymmetricPackage.eINSTANCE;
	
	private static final MatchingModelPackage MATCHING_PACKAGE = MatchingModelPackage.eINSTANCE;
	
	private LiftingGraphDomainMap domainMap;
	
	private LiftingGraphIndex changeIndex;
	
	public LiftingGraphEngine(LiftingGraphDomainMap domainMap, LiftingGraphIndex changeIndex) {
		this.domainMap = domainMap;
		this.changeIndex = changeIndex;
	}
	
	@Override
	public ReferenceConstraint createCrossReferenceConstraint(Variable target, final Edge incoming) {
		
		// ModelA <-matchedA- Correspondence:
		if (incoming.getType() == MATCHING_PACKAGE.getCorrespondence_MatchedA()) {
			CrossReferenceConstraint constraint = new CrossReferenceConstraint(target, incoming) {
				
				@Override
				public List<EObject> getCrossReferenced(EObject source, Edge incoming) {
					Correspondence correspondence = domainMap.getDifference().getCorrespondenceOfModelA(source);
					
					if (correspondence != null) {
						List<EObject> modifiableSingelton = new ArrayList<EObject>(1);
						modifiableSingelton.add(correspondence);
						return modifiableSingelton;
					} else {
						// Explicitly no match!
						return Collections.emptyList();
					}
				}
			};
			
			return constraint;
		}
		
		// ModelB <-matchedB- Correspondence:
		if (incoming.getType() == MATCHING_PACKAGE.getCorrespondence_MatchedB()) {
			CrossReferenceConstraint constraint = new CrossReferenceConstraint(target, incoming) {
				
				@Override
				public List<EObject> getCrossReferenced(EObject source, Edge incoming) {
					Correspondence correspondence =  domainMap.getDifference().getCorrespondenceOfModelB(source);
					
					if (correspondence != null) {
						List<EObject> modifiableSingelton = new ArrayList<EObject>(1);
						modifiableSingelton.add(correspondence);
						return modifiableSingelton;
					} else {
						// Explicitly no match!
						return Collections.emptyList();
					}
				}
			};
			
			return constraint;
		}
		
		// EReference <-type- RemoveReference/AddReference/AttributeValueChange:
		if ((incoming.getType() == SYMMETRIC_PACKAGE.getRemoveReference_Type()) 
				|| (incoming.getType() == SYMMETRIC_PACKAGE.getAddReference_Type())
				|| (incoming.getType() == SYMMETRIC_PACKAGE.getAttributeValueChange_Type())) {
			
			CrossReferenceConstraint constraint = new CrossReferenceConstraint(target, incoming) {

				@Override
				public List<EObject> getCrossReferenced(EObject source, Edge incoming) {
					return domainMap.getChangeDomain(incoming.getSource().getType(), source);
				}
			};

			return constraint;
		}
		
		// We should flag this as optional -> normally the node sorting prevents this case -> indexing overhead!
		// ModelElement <-objX/src/tgt- AddObject/RemoveObject/RemoveReference/AddReference/AttributeValueChange:
		if ((incoming.getType() == SYMMETRIC_PACKAGE.getAddObject_Obj()
				|| (incoming.getType() == SYMMETRIC_PACKAGE.getRemoveObject_Obj())) 
				|| (incoming.getType() == SYMMETRIC_PACKAGE.getAddReference_Src())
				|| (incoming.getType() == SYMMETRIC_PACKAGE.getAddReference_Tgt())
				|| (incoming.getType() == SYMMETRIC_PACKAGE.getRemoveReference_Src())
				|| (incoming.getType() == SYMMETRIC_PACKAGE.getRemoveReference_Tgt())
				|| (incoming.getType() == SYMMETRIC_PACKAGE.getAttributeValueChange_ObjA())
				|| (incoming.getType() == SYMMETRIC_PACKAGE.getAttributeValueChange_ObjB())) {
			
			assert (Change.class.isAssignableFrom(incoming.getSource().getType().getInstanceClass()));
			
			CrossReferenceConstraint constraint = new CrossReferenceConstraint(target, incoming) {

				@Override
				public List<EObject> getCrossReferenced(EObject source, Edge incoming) {
					List<EObject> localChanges = new ArrayList<EObject>();

					@SuppressWarnings("unchecked")
					Iterator<? extends Change> changeIterator = changeIndex.getLocalChanges(
							source, incoming.getType(), 
							(Class<? extends Change>) incoming.getSource().getType().getInstanceClass());
					
					while (changeIterator.hasNext()) {
						localChanges.add(changeIterator.next());
					}
					
					return localChanges;
				}
			};

			return constraint;
		}

		return null;
	}
}

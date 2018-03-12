package org.sidiff.difference.lifting.recognitionengine.graph;

import static org.sidiff.difference.lifting.recognitionengine.rules.RecognitionRuleUtil.isChangeType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.difference.lifting.recognitionengine.rules.RecognitionRuleBlueprint;
import org.sidiff.difference.symmetric.SymmetricPackage;
import org.sidiff.matching.model.MatchingModelPackage;

/**
 * A graph view for a single recognition rule.
 * 
 * @author Manuel Ohrndorf
 */
public class LiftingGraphProxy implements EGraph {

	private static final SymmetricPackage SYMMETRIC_PACKAGE = SymmetricPackage.eINSTANCE;
	
	private static final MatchingModelPackage MATCHING_PACKAGE = MatchingModelPackage.eINSTANCE;
	
	/**
	 * The corresponding recognition rule.
	 */
	private Rule recognitionRule;
	
	/**
	 * The change type blueprint of the corresponding recognition rule.
	 */
	private RecognitionRuleBlueprint recognitionRuleBlueprint;
	
	/**
	 * The common graph of model A.
	 */
	private EGraph modelAGraph;
	
	/**
	 * The common graph of model B.
	 */
	private EGraph modelBGraph;
	
	/**
	 * The common domain map / "graph" of the difference.
	 */
	private LiftingGraphDomainMap liftingGraphDomainMap;
	
	/**
	 * All meta model types.
	 */
	private List<EObject> types = new ArrayList<EObject>();
	
	public LiftingGraphProxy(Rule recognitionRule, RecognitionRuleBlueprint recognitionRuleBlueprint,
			EGraph modelAGraph, EGraph modelBGraph, LiftingGraphDomainMap liftingGraphDomainMap) {
		super();
		this.recognitionRule = recognitionRule;
		this.recognitionRuleBlueprint = recognitionRuleBlueprint;
		this.modelAGraph = modelAGraph;
		this.modelBGraph = modelBGraph;
		this.liftingGraphDomainMap = liftingGraphDomainMap;
		
		// Add multi-rules to the blueprint:
		// TODO: Reuse or copy the blueprint for multi-rules!?
		recognitionRuleBlueprint.appendMultiRules();
		
		// Search for meta model types in recognition rule:
		for (EAttribute metaAttribute : recognitionRuleBlueprint.attributeValueChange.keySet()) {
			types.add(metaAttribute);
		}
		
		for (EReference metaReference : recognitionRuleBlueprint.removeReference.keySet()) {
			types.add(metaReference);
		}
		
		for (EReference metaReference : recognitionRuleBlueprint.addReference.keySet()) {
			types.add(metaReference);
		}
	}
	
	@Override
	public List<EObject> getDomain(EClass type, boolean strict) {
		// NOTE: Always synchronize this implementation with getDomainSize()!
		
		// Add-/Remove-Object, Add-/Remove-Reference, Attribute-Value-Change:
		if (isChangeType(type)) {
			List<EObject> domain = new ArrayList<EObject>(getDomainSize(type, strict));
			
			for (EObject changeDomainType : getAggregatedChangeDomainTypes(type)) {
				domain.addAll(liftingGraphDomainMap.getChangeDomain(type, changeDomainType));
			}
			
			return domain;
		} 
		
		// Correspondence:
		else if (type == MATCHING_PACKAGE.getCorrespondence()) {
			return new ArrayList<EObject>(liftingGraphDomainMap.getDifference().getMatching().getCorrespondences());
		}
		
		// SymmetricDifference:
		else if (type == SYMMETRIC_PACKAGE.getSymmetricDifference()) {
			return new ArrayList<EObject>(Collections.singletonList(liftingGraphDomainMap.getDifference()));
		}
		
		// Model:
		else {
			List<EObject> domainA = modelAGraph.getDomain(type, strict);
			List<EObject> domainB = modelBGraph.getDomain(type, strict);
			
			List<EObject> result;
			
			if (domainA.isEmpty()) {
				result = domainB;
			} else {
				domainA.addAll(domainB);
				result = domainA;
			}
			
			// Meta-model type node:
			// TODO: Set types as partial match!
			if ((type == EcorePackage.eINSTANCE.getEReference()) || (type == EcorePackage.eINSTANCE.getEAttribute())) {
				result.addAll(types);
			}

			return result;
		}
	}

	@Override
	public int getDomainSize(EClass type, boolean strict) {
		// NOTE: Always synchronize this implementation with getDomain()!
		
		// Add-/Remove-Object, Add-/Remove-Reference, Attribute-Value-Change:
		if (isChangeType(type)) {
			int domainSize = 0;
			
			for (EObject changeDomainType : getAggregatedChangeDomainTypes(type)) {
				domainSize += liftingGraphDomainMap.getChangeDomainSize(type, changeDomainType);
			}
			
			return domainSize;
		} 
		
		// Correspondence:
		else if (type == MATCHING_PACKAGE.getCorrespondence()) {
			return liftingGraphDomainMap.getDifference().getMatching().getCorrespondences().size();
		}
		
		// SymmetricDifference:
		else if (type == SYMMETRIC_PACKAGE.getSymmetricDifference()) {
			return getDomain(type, strict).size();
		}
		
		// Model:
		else {
			int domainASize = modelAGraph.getDomainSize(type, strict);
			int domainBSize = modelBGraph.getDomainSize(type, strict);
			
			// Meta-model type node:
			if ((type == EcorePackage.eINSTANCE.getEReference()) || (type == EcorePackage.eINSTANCE.getEAttribute())) {
				return domainASize + domainBSize + liftingGraphDomainMap.getTypeNodes().size();
			} else {
				return domainASize + domainBSize;
			}
		}
	}
	
	@Override
	public ECrossReferenceAdapter getCrossReferenceAdapter() {
		// TODO: Check if a cross-referencer is thread safe.
		// Only needed for << delete >> nodes.
		return null;
	}
	
	private Set<? extends EObject> getAggregatedChangeDomainTypes(EClass changeType) {
		
		// AddObject:
		if (changeType == SYMMETRIC_PACKAGE.getAddObject()) {
			return recognitionRuleBlueprint.addObject.keySet();
		}

		// RemoveObject:
		else if (changeType == SYMMETRIC_PACKAGE.getRemoveObject()) {
			return recognitionRuleBlueprint.removeObject.keySet();
		}

		// AddReference:
		else if (changeType == SYMMETRIC_PACKAGE.getAddReference()) {
			return recognitionRuleBlueprint.addReference.keySet();
		}

		// RemoveReference:
		else if (changeType == SYMMETRIC_PACKAGE.getRemoveReference()) {
			return recognitionRuleBlueprint.removeReference.keySet();
		}

		// AttributeValueChange:
		else if (changeType == SYMMETRIC_PACKAGE.getAttributeValueChange()) {
			return recognitionRuleBlueprint.attributeValueChange.keySet();
		}
		
		return null;
	}
		
	public Rule getRecognitionRule() {
		return recognitionRule;
	}

	
	public void setRecognitionRule(Rule recognitionRule) {
		this.recognitionRule = recognitionRule;
	}
	
	@Override
	public boolean add(EObject e) {
		// NOTE: Ignore the adding of the Semantic-Change-Sets...
		return true; 
	}
	
	// -----------------------------------------------------------------------------------------------------------------
	// TODO: Calculate dynamic graph portion!
	
	@Override
	public int size() {
		return modelAGraph.size() + modelBGraph.size();
	}

	@Override
	public boolean isEmpty() {
		return modelAGraph.isEmpty() && modelBGraph.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return modelAGraph.contains(o) || modelBGraph.contains(o);
	}

	@Override
	public Iterator<EObject> iterator() {
		return new Iterator<EObject>() {

			Iterator<EObject> itA = modelAGraph.iterator();
			Iterator<EObject> itB = modelBGraph.iterator();
			
			@Override
			public boolean hasNext() {
				return itA.hasNext() || itB.hasNext();
			}

			@Override
			public EObject next() {
				if (itA.hasNext()) {
					return itA.next();
				} else {
					return itB.next();
				}
			}
		};
	}

	@Override
	public Object[] toArray() {
		// Dynamically...
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// Dynamically...
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		// Dynamically...
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// Dynamically...
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends EObject> c) {
		// Dynamically...
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// Dynamically...
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// Dynamically...
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		// Dynamically...
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addTree(EObject root) {
		// Dynamically...
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addGraph(EObject object) {
		// Dynamically...
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeTree(EObject root) {
		// Dynamically...
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeGraph(EObject object) {
		// Dynamically...
		throw new UnsupportedOperationException();
	}

	@Override
	public EGraph copy(Map<EObject, EObject> copies) {
		// Dynamically...
		throw new UnsupportedOperationException();
	}

	@Override
	public List<EObject> getRoots() {
		// Dynamically...
		throw new UnsupportedOperationException();
	}
}

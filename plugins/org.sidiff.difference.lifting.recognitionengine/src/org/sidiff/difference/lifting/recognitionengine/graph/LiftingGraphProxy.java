package org.sidiff.difference.lifting.recognitionengine.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.difference.lifting.recognitionengine.ruleapplication.RecognitionRuleFilterBlueprint;
import org.sidiff.difference.symmetric.SymmetricPackage;

/**
 * A graph view for a single recognition rule.
 * 
 * @author Manuel Ohrndorf
 */
public class LiftingGraphProxy implements EGraph {

	/**
	 * The corresponding recognition rule.
	 */
	private Rule recognitionRule;
	
	/**
	 * The change type blueprint of the corresponding recognition rule.
	 */
	private RecognitionRuleFilterBlueprint recognitionRuleBlueprint;
	
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
	private DifferenceDomainMap differenceDomainMap;
	
//	/**
//	 * The common cross-reference adapter.
//	 */
//	private ECrossReferenceAdapter crossReferenceAdapter;
	
//	private Map<EClass, List<EObject>> domainMapCache;
	
	public LiftingGraphProxy(Rule recognitionRule, RecognitionRuleFilterBlueprint recognitionRuleBlueprint,
			EGraph modelAGraph, EGraph modelBGraph, DifferenceDomainMap differenceDomainMap) {
		super();
		this.recognitionRule = recognitionRule;
		this.recognitionRuleBlueprint = recognitionRuleBlueprint;
		this.modelAGraph = modelAGraph;
		this.modelBGraph = modelBGraph;
		this.differenceDomainMap = differenceDomainMap;
	}
	
	@Override
	public List<EObject> getDomain(EClass type, boolean strict) {
		// NOTE: Always synchronize this implementation with getDomainSize()!
		
		if (isDifferenceType(type)) {
			// TODO: Can be optimized if the variable is known!
			List<EObject> domain = new ArrayList<EObject>(getDomainSize(type, strict));
			
			for (EObject changeDomainType : getAggregatedChangeDomainTypes(type)) {
				domain.addAll(differenceDomainMap.getChangeDomain(type, changeDomainType));
			}
			
			return domain;
		} else {
			// TODO: Can be optimized if the variable is known!
			List<EObject> domainA = modelAGraph.getDomain(type, strict);
			List<EObject> domainB = modelBGraph.getDomain(type, strict);
			
			domainA.addAll(domainB);
			return domainA;
		}
	}

	@Override
	public int getDomainSize(EClass type, boolean strict) {
		// NOTE: Always synchronize this implementation with getDomain()!
		
		if (isDifferenceType(type)) {
			int domainSize = 0;
			
			for (EObject changeDomainType : getAggregatedChangeDomainTypes(type)) {
				domainSize += differenceDomainMap.getChangeDomainSize(type, changeDomainType);
			}
			
			return domainSize;
		} else {
			int domainASize = modelAGraph.getDomainSize(type, strict);
			int domainBSize = modelBGraph.getDomainSize(type, strict);
			
			return domainASize + domainBSize;
		}
	}
	
	@Override
	public ECrossReferenceAdapter getCrossReferenceAdapter() {
		// TODO: Check if a cross-referencer is thread safe.
		// Only needed for << delete >> nodes.
		return null;
	}
	
	private static List<EObject> getAggregatedChangeDomainTypes(EClass changeType) {
		// TODO
		return null;
	}
	
	private static boolean isDifferenceType(EClass type) {
		
		if ((type == SymmetricPackage.eINSTANCE.getAddObject())
				|| (type == SymmetricPackage.eINSTANCE.getRemoveObject())
				|| (type == SymmetricPackage.eINSTANCE.getAddReference())
				|| (type == SymmetricPackage.eINSTANCE.getRemoveReference())
				|| (type == SymmetricPackage.eINSTANCE.getAttributeValueChange())) {
			return true;
		}
		
		return false;
	}
		
	public Rule getRecognitionRule() {
		return recognitionRule;
	}

	
	public void setRecognitionRule(Rule recognitionRule) {
		this.recognitionRule = recognitionRule;
	}
	
	// -----------------------------------------------------------------------------------------------------------------
	
	@Override
	public int size() {
		// Dynamically...
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEmpty() {
		// Dynamically...
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(Object o) {
		// Dynamically...
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<EObject> iterator() {
		// Dynamically...
		throw new UnsupportedOperationException();
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
	public boolean add(EObject e) {
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

package org.eclipse.emf.henshin.interpreter.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.info.RuleInfo;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Extends the default {@link EGraph} API to node-specific domain restrictions.
 * 
 * @author Manuel Ohrndorf
 */
public class RestrictedEGraphImpl implements EGraph {

	/**
	 * The default graph for all none restricted nodes.
	 */
	protected final EGraph baseGraph;

	/**
	 * Mappings from a restricted node/variable to all its instances.
	 */
	protected final Map<Node, Collection<? extends EObject>> restrictedDomainMap;

	/**
	 * We need the rule-info to translate between nodes and variables.
	 */
	protected final RuleInfo ruleInfo;

	/**
	 * Extends the default {@link EGraph} API to node-specific domain restrictions.
	 * 
	 * @param baseGraph
	 *            The default graph for all none restricted nodes.
	 * @param engine
	 *            The corresponding engine which uses this
	 *            {@link RestrictedEGraphImpl}.
	 * @param rule
	 *            The rule that will be applied to this
	 *            {@link RestrictedEGraphImpl}.
	 */
	public RestrictedEGraphImpl(EGraph baseGraph, EngineImpl engine, Rule rule) {
		this.baseGraph = baseGraph;
		this.restrictedDomainMap = new LinkedHashMap<Node, Collection<? extends EObject>>();
		this.ruleInfo = engine.getRuleInfo(rule);
	}
	
	/**
	 * @param node
	 *            A node of the rule which corresponds to this
	 *            {@link RestrictedEGraphImpl}.
	 * @param domain
	 *            The restricted node domain.
	 */
	public void setDomainRestriction(Node node, Collection<? extends EObject> domain) {
		restrictedDomainMap.put(node, domain);
	}

	/**
	 * @param node
	 *            A node of the rule which corresponds to this
	 *            {@link RestrictedEGraphImpl}.
	 * @param domain
	 *            The restricted node domain.
	 */
	public void setDomainRestriction(Node node, EObject domain) {
		restrictedDomainMap.put(node, Collections.singletonList(domain));
	}

	/**
	 * @param node
	 *            A node of the rule which corresponds to this
	 *            {@link RestrictedEGraphImpl}.
	 * @return An unmodifiable list of the restricted node domain.
	 */
	public Collection<? extends EObject> getDomainRestriction(Node node) {
		return Collections.unmodifiableCollection(restrictedDomainMap.get(nodeToVariable(node)));
	}
	
	private Variable nodeToVariable(Node node) {
		return ruleInfo.getVariableInfo().getNode2variable().get(node);
	}

	/**
	 * @return The corresponding rule of this {@link RestrictedEGraphImpl}.
	 */
	public Rule getRule() {
		return ruleInfo.getRule();
	}
	
	/**
	 * Get all {@link EObject}s of this graph which are compatible with the
	 * given type. This returns a fresh and modifiable list.
	 * 
	 * @param type
	 *            The type of the objects.
	 * @param strict
	 *            Whether subtypes are excluded from the result.
	 * @param variable
	 *            The variable which will be assigned.
	 * @return A set of {@link EObject}s compatible with the type.
	 */
	public List<EObject> getDomain(EClass type, boolean strict, Variable variable) {
		Node node = ruleInfo.getVariableInfo().getVariableForNode(variable);
		Collection<? extends EObject> restricedDomain = restrictedDomainMap.get(node);
		
		if (restricedDomain != null) {
			return new ArrayList<EObject>(restricedDomain);
		} else {
			return baseGraph.getDomain(type, strict);
		}
	}

	/**
	 * Returns the size of the domain for a type. The returned number equals the
	 * size of the list returned by {@link #getDomain(EClass, boolean)}. This
	 * method should be used whenever the actual objects are not needed.
	 * 
	 * @param type
	 *            The type.
	 * @param strict
	 *            Whether subtypes are excluded.
	 * @param variable
	 *            The variable which will be assigned.
	 * @return The size of the domain.
	 */
	public int getDomainSize(EClass type, boolean strict, Variable variable) {
		Node node = ruleInfo.getVariableInfo().getVariableForNode(variable);
		Collection<? extends EObject> restricedDomain = restrictedDomainMap.get(node);
		
		if (restricedDomain != null) {
			return restricedDomain.size();
		} else {
			return baseGraph.getDomainSize(type, strict);
		}
	}

	@Override
	public int size() {
		return baseGraph.size();
	}

	@Override
	public boolean isEmpty() {
		return baseGraph.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return baseGraph.contains(o);
	}

	@Override
	public Iterator<EObject> iterator() {
		return baseGraph.iterator();
	}

	@Override
	public Object[] toArray() {
		return baseGraph.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return baseGraph.toArray(a);
	}

	@Override
	public boolean add(EObject e) {
		return baseGraph.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return baseGraph.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return baseGraph.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends EObject> c) {
		return baseGraph.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return baseGraph.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return baseGraph.retainAll(c);
	}

	@Override
	public void clear() {
		baseGraph.clear();
	}

	@Override
	public boolean addTree(EObject root) {
		return baseGraph.addTree(root);
	}

	@Override
	public boolean addGraph(EObject object) {
		return baseGraph.addGraph(object);
	}

	@Override
	public boolean removeTree(EObject root) {
		return baseGraph.removeTree(root);
	}

	@Override
	public boolean removeGraph(EObject object) {
		return baseGraph.remove(object);
	}

	@Override
	public EGraph copy(Map<EObject, EObject> copies) {
		return baseGraph.copy(copies);
	}

	@Override
	public List<EObject> getDomain(EClass type, boolean strict) {
		return baseGraph.getDomain(type, strict);
	}

	@Override
	public int getDomainSize(EClass type, boolean strict) {
		return baseGraph.getDomainSize(type, strict);
	}

	@Override
	public List<EObject> getRoots() {
		return baseGraph.getRoots();
	}

	@Override
	public ECrossReferenceAdapter getCrossReferenceAdapter() {
		return baseGraph.getCrossReferenceAdapter();
	}
}

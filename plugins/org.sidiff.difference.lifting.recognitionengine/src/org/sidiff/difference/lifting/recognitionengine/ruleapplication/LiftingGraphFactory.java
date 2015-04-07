package org.sidiff.difference.lifting.recognitionengine.ruleapplication;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.symmetric.AddObject;
import org.sidiff.difference.symmetric.AddReference;
import org.sidiff.difference.symmetric.AttributeValueChange;
import org.sidiff.difference.symmetric.Change;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.RemoveObject;
import org.sidiff.difference.symmetric.RemoveReference;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.silift.common.util.emf.Scope;

/**
 * Factory that creates a Henshin graph which contains all objects that are
 * needed in order to recognize the SemanticChangeSets when lifting the
 * SymmetricDifference "difference".
 * 
 * @author kehrer
 */
public class LiftingGraphFactory {

	/**
	 * The difference to be semantically lifted
	 */
	private SymmetricDifference difference;

	/**
	 * Imported objects that must be added to the Henshin graph
	 */
	private Set<EObject> imports;

	/**
	 * The scope (single resource or complete resource set).
	 */
	private Scope scope;

	/**
	 * <ul>
	 * <li><code>true</code>: Builds a minimal working graph for each
	 * Recognition-Rule.
	 * <li><code>false</code>: Build a single working graph for all
	 * Recognition-Rule.
	 * </ul>
	 */
	private boolean buildGraphPerRule;

	/**
	 * The single working graph if {@link LiftingGraphFactory#buildGraphPerRule}
	 * is <code>false</code>
	 */
	private EGraph fullEGraph;

	// private Map<EObject, Set<Adapter>> object2Adapters;

	/**
	 * Constructs a new graph factory for the given difference and the set of
	 * imported eObjects.
	 * 
	 * @param difference
	 *            The difference to be semantically lifted
	 * @param imports
	 *            Imported objects that must be added to the Henshin graph
	 */
	public LiftingGraphFactory(SymmetricDifference difference, Set<EObject> imports, Scope scope,
			boolean buildGraphPerRule) {
		super();
		this.difference = difference;
		this.imports = imports;
		this.scope = scope;
		this.buildGraphPerRule = buildGraphPerRule;
		// this.object2Adapters = new HashMap<EObject, Set<Adapter>>();
	}

	/**
	 * Use a single working graph for all Recognition-Rules or create a minimal
	 * working graph for each Recognition-Rule.
	 * 
	 * @param rr
	 *            The Recognition-Rule to execute.
	 * @return The working graph.
	 */
	public synchronized EGraph getEGraph(Rule rr) {
		if (buildGraphPerRule) {
			return createEGraph(rr);
		} else {
			if (fullEGraph == null) {
				fullEGraph = createSingleEGraph();
			}
			return fullEGraph;
		}
	}

	/**
	 * Creates a Henshin graph which is optimized to the recognition rule rr. If
	 * rr is null, a complete Henshin graph will be returned.
	 * 
	 * This method can be called by the recognizer threads when they want to
	 * obtain sn optimized graph for their matching job. Thus, this method is
	 * synchronized!
	 * 
	 * Please note that every client that creates a search graph is responsible
	 * for cleaning up this graph as soon as it is not needed any more by
	 * calling {@link #cleanupGraph(EGraph)}!
	 * 
	 * @param rr
	 * @return
	 */
	protected synchronized EGraph createEGraph(Rule rr) {
		EGraph graph = new EGraphImpl();

		RecognitionRuleGraphBlueprint blueprint = null;
		if (rr != null) {
			blueprint = new RecognitionRuleGraphBlueprint(rr);
		}

		// Add Difference-Model and the required types from metamodel to graph
		if (rr != null) {
			graph.add(difference);
			for (Change change : difference.getChanges()) {
				if (change instanceof AddObject) {
					EClass type = ((AddObject) change).getObj().eClass();
					if (blueprint.addObject.contains(type)
							|| !Collections.disjoint(blueprint.addObject, type.getEAllSuperTypes())) {
						graph.add(change);
					}
					continue;
				}
				if (change instanceof RemoveObject) {
					EClass type = ((RemoveObject) change).getObj().eClass();
					if (blueprint.removeObject.contains(type)
							|| !Collections.disjoint(blueprint.removeObject, type.getEAllSuperTypes())) {
						graph.add(change);
					}
					continue;
				}
				if (change instanceof AddReference) {
					EReference type = ((AddReference) change).getType();
					String typeStr = type.getName();
					if (blueprint.addReference.contains(typeStr)) {
						graph.add(change);
						graph.add(type);
					}
					continue;
				}
				if (change instanceof RemoveReference) {
					EReference type = ((RemoveReference) change).getType();
					String typeStr = type.getName();
					if (blueprint.removeReference.contains(typeStr)) {
						graph.add(change);
						graph.add(type);
					}
					continue;
				}
				if (change instanceof AttributeValueChange) {
					EAttribute type = ((AttributeValueChange) change).getType();
					String typeStr = type.getName();
					if (blueprint.attrValueChange.contains(typeStr)) {
						graph.add(change);
						// no that type is added by addEObjectToGraph()
					}
					continue;
				}
			}
			for (Correspondence correspondence : difference.getCorrespondences()) {
				// TODO: we can also filter correspondences
				graph.add(correspondence);
			}
		} else {
			LogUtil.log(LogEvent.DEBUG, "[Root] Difference: " + difference);
			graph.addTree(difference);

			for (Change change : difference.getChanges()) {
				EStructuralFeature type = null;
				if (change instanceof AddReference) {
					type = ((AddReference) change).getType();
				}
				if (change instanceof RemoveReference) {
					type = ((RemoveReference) change).getType();
				}
				// Note that attribute types are added by addEObjectToGraph()
				if (type != null) {
					boolean added = graph.add(type);
					if (added)
						LogUtil.log(LogEvent.DEBUG, "       Metamodel: " + type);
				}
			}
		}

		// Add model A to graph
		for (Iterator<EObject> iterator = difference.getModelA().getAllContents(); iterator.hasNext();) {
			EObject eObject = iterator.next();
			if (rr != null) {
				if (blueprint.modelTypes.contains(eObject.eClass())
						|| !Collections.disjoint(blueprint.modelTypes, eObject.eClass().getEAllSuperTypes())) {
					addEObjectToGraph(eObject, graph);
				}
			} else {
				addEObjectToGraph(eObject, graph);
			}
		}

		// Add all resources of resource set A if mode = "complete resource set"
		if (scope == Scope.RESOURCE_SET) {
			for (Resource r : difference.getModelA().getResourceSet().getResources()) {
				if (r == difference.getModelA()) {
					continue;
				}

				for (Iterator<EObject> iterator = r.getAllContents(); iterator.hasNext();) {
					EObject eObject = iterator.next();
					if (rr != null) {
						if (blueprint.modelTypes.contains(eObject.eClass())
								|| !Collections.disjoint(blueprint.modelTypes, eObject.eClass().getEAllSuperTypes())) {
							addEObjectToGraph(eObject, graph);
						}
					} else {
						addEObjectToGraph(eObject, graph);
					}
				}
			}
		}

		// Add model B to graph
		for (Iterator<EObject> iterator = difference.getModelB().getAllContents(); iterator.hasNext();) {
			EObject eObject = iterator.next();
			if (rr != null) {
				if (blueprint.modelTypes.contains(eObject.eClass())
						|| !Collections.disjoint(blueprint.modelTypes, eObject.eClass().getEAllSuperTypes())) {
					addEObjectToGraph(eObject, graph);
				}
			} else {
				addEObjectToGraph(eObject, graph);
			}
		}

		// Add all resources of resource set B if mode = "complete resource set"
		if (scope == Scope.RESOURCE_SET) {
			for (Resource r : difference.getModelB().getResourceSet().getResources()) {
				if (r == difference.getModelB()) {
					continue;
				}

				for (Iterator<EObject> iterator = r.getAllContents(); iterator.hasNext();) {
					EObject eObject = iterator.next();
					if (rr != null) {
						if (blueprint.modelTypes.contains(eObject.eClass())
								|| !Collections.disjoint(blueprint.modelTypes, eObject.eClass().getEAllSuperTypes())) {
							addEObjectToGraph(eObject, graph);
						}
					} else {
						addEObjectToGraph(eObject, graph);
					}
				}
			}
		}

		// Add imports to graph
		if (imports != null) {
			for (EObject eObject : imports) {
				if (rr != null) {
					if (blueprint.modelTypes.contains(eObject.eClass())
							|| !Collections.disjoint(blueprint.modelTypes, eObject.eClass().getEAllSuperTypes())) {
						addEObjectToGraph(eObject, graph);
					}
				} else {
					addEObjectToGraph(eObject, graph);
				}
			}
		}

		return graph;
	}

	/**
	 * Adds the EObject and the types of all attribute of the EObject's class.
	 * This is due to the fact that we can never be sure which attribute types
	 * are needed in the graph, due to optional AVCs with a reference to its
	 * attribute type which is in the kernel node of a RR.
	 * 
	 * @param object
	 * @param graph
	 */
	private void addEObjectToGraph(EObject object, EGraph graph) {
		graph.add(object);
		// add also the attribute types used by the given eObject
		for (EAttribute attribute : object.eClass().getEAllAttributes()) {
			if (object.eGet(attribute) != null){
				graph.add(attribute);
			}
		}
	}

	//
	// private void cacheAdapters() {
	// object2Adapters = new HashMap<EObject, Set<Adapter>>();
	//
	// // Adapters for difference model
	// for (Iterator<EObject> iterator = difference.eAllContents();
	// iterator.hasNext();) {
	// EObject object = iterator.next();
	// cacheAdapters(object);
	// EStructuralFeature type = null;
	// if (object instanceof AddReference) {
	// type = ((AddReference) object).getType();
	// cacheAdapters(type);
	// }
	// if (object instanceof RemoveReference) {
	// type = ((RemoveReference) object).getType();
	// cacheAdapters(type);
	// }
	// if (object instanceof AttributeValueChange) {
	// type = ((AttributeValueChange) object).getType();
	// cacheAdapters(type);
	// }
	// }
	// }
	//
	// private void cacheAdapters(EObject object){
	// if (!object2Adapters.containsKey(object)){
	// Set<Adapter> adapters = new HashSet<Adapter>(object.eAdapters());
	// object2Adapters.put(object, adapters);
	// }
	// }

	/**
	 * Creates a single working graph for all Recognition-Rules.
	 * 
	 * @param imports
	 *            The imports to merge.
	 * @return The working graph.
	 */
	protected synchronized EGraph createSingleEGraph() {

		// Initialize Henshin graph
		EGraph graph = new EGraphImpl();

		// Build Henshin graph (filter duplicated)
		Set<EObject> rootObjects = new HashSet<EObject>();

		// Add Difference-Model to graph
		rootObjects.add(difference);
		LogUtil.log(LogEvent.DEBUG, "[Root] Difference: " + difference);

//		// Add Ecore to the graph
//		rootObjects.add(EcorePackage.eINSTANCE);
//		LogUtil.log(LogEvent.DEBUG, "[Root] Ecore: " + EcorePackage.eINSTANCE);

		// Add the required types from the meta-model to the graph
		for (Change change : difference.getChanges()) {
			EStructuralFeature type = null;
			if (change instanceof AddReference) {
				type = ((AddReference) change).getType();
			}
			if (change instanceof RemoveReference) {
				type = ((RemoveReference) change).getType();
			}
			if (type != null) {
				boolean added = graph.add(type);
				if (added)
					LogUtil.log(LogEvent.DEBUG, "       Metamodel: " + type);
			}
		}

		// Add model A to graph
		for (EObject rootObj : difference.getModelA().getContents()) {
			rootObjects.add(rootObj);
			LogUtil.log(LogEvent.DEBUG, "[Root] Model A: " + rootObj);
		}

		// Add all resources of resource set A if mode = "complete resource set"
		if (scope == Scope.RESOURCE_SET) {
			for (Resource r : difference.getModelA().getResourceSet().getResources()) {
				if (r == difference.getModelA()) {
					continue;
				}
				for (EObject rootObj : r.getContents()) {
					rootObjects.add(rootObj);
					LogUtil.log(LogEvent.DEBUG, "[Root] Res.Set A: " + rootObj);
				}
			}
		}

		// Add model B to graph
		for (EObject rootObj : difference.getModelB().getContents()) {
			rootObjects.add(rootObj);
			LogUtil.log(LogEvent.DEBUG, "[Root] Model B: " + rootObj);
		}

		// Add all resources of resource set B if mode = "complete resource set"
		if (scope == Scope.RESOURCE_SET) {
			for (Resource r : difference.getModelB().getResourceSet().getResources()) {
				if (r == difference.getModelB()) {
					continue;
				}
				for (EObject rootObj : r.getContents()) {
					rootObjects.add(rootObj);
					LogUtil.log(LogEvent.DEBUG, "[Root] Res.Set B: " + rootObj);
				}
			}
		}

		// Add root objects (and all their contents) to graph
		for (EObject root : rootObjects) {
			addEObjectToGraph(root, graph);
			for (Iterator<EObject> iterator = root.eAllContents(); iterator.hasNext();) {
				EObject eObject = iterator.next();
				addEObjectToGraph(eObject, graph);
			}
		}

		// Add imports to graph
		if (imports != null) {
			for (EObject eObject : imports) {
				addEObjectToGraph(eObject, graph);
			}
		}
		return graph;
	}
	
	/**
	 * @return
	 * 
	 * <ul>
	 *  <li><code>true</code>: Builds a minimal working graph for each Recognition-Rule.
	 *  <li><code>false</code>: Build a single working graph for all Recognition-Rule.
	 * </ul>
	 */
	public boolean isBuildGraphPerRule() {
		return buildGraphPerRule;
	}
	
	/**
	 * @param buildGraphPerRule
	 *
	 * <ul>
	 *  <li><code>true</code>: Builds a minimal working graph for each Recognition-Rule.
	 *  <li><code>false</code>: Build a single working graph for all Recognition-Rule.
	 * </ul>
	 */
	public void setBuildGraphPerRule(boolean buildGraphPerRule) {
		this.buildGraphPerRule = buildGraphPerRule;
	}
}

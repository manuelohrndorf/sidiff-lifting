package org.sidiff.difference.lifting.recognitionengine.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.lifting.recognitionengine.rules.RecognitionRuleBlueprint;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.mergeimports.ModelImports;

/**
 * Factory that creates a Henshin graph which contains all model element (of
 * model A and B) which are relevant for a corresponding difference. The
 * difference itself will be dynamically shown to the Henshin interpreter by
 * the {@link LiftingGraphProxy}. Which optimizes the difference graph for a
 * given recognition rule.
 */
public class LiftingGraphFactory {

	/**
	 * The difference to be semantically lifted.
	 */
	private SymmetricDifference difference;
	
	/**
	 * The corresponding difference index knowing the low-level changes per type.
	 */
	private LiftingGraphDomainMap liftingGraphDomainMap;

	/**
	 * Imported objects that must be added to the Henshin graph.
	 */
	private ModelImports imports;

	/**
	 * The scope (single resource or complete resource set).
	 */
	private Scope scope;

	/**
	 * The working graph model/resource A (and import).
	 */
	private EGraph modelAGraph;
	
	/**
	 * The working graph model/resource B (and import).
	 */
	private EGraph modelBGraph;
	
	/**
	 * The single working graph if {@link LiftingGraphFactory#buildGraphPerRule} is <code>false</code>
	 */
	private EGraph fullEGraph;
	
	/**
	 * <ul>
	 * <li><code>true</code>: Builds a minimal working graph for each Recognition-Rule.
	 * <li><code>false</code>: Build a single working graph for all Recognition-Rule.
	 * </ul>
	 */
	private boolean buildGraphPerRule;

	/**
	 * Constructs a new graph factory for the given difference and the set of
	 * imported eObjects.
	 * 
	 * @param buildGraphPerRule
	 *            <ul>
	 *            <li><code>true</code>: Builds a minimal working graph for each Recognition-Rule.
	 *            <li><code>false</code>: Build a single working graph for all Recognition-Rule.
	 *            </ul>
	 * @param liftingGraphDomainMap
	 *            The corresponding difference index knowing the low-level
	 *            changes per type.
	 * @param imports
	 *            Imported objects that must be added to the Henshin graph.
	 * @param scope
	 *            The scope (single resource or complete resource set).
	 */
	public LiftingGraphFactory(boolean buildGraphPerRule, 
			LiftingGraphDomainMap liftingGraphDomainMap, 
			ModelImports imports, Scope scope) {
		
		this.buildGraphPerRule = buildGraphPerRule;
		this.difference = liftingGraphDomainMap.getDifference();
		this.liftingGraphDomainMap = liftingGraphDomainMap;
		this.imports = imports;
		this.scope = scope;
		
		// Model graphs:
		final CountDownLatch barrier = new CountDownLatch(2);
		
		// Model A:
		Thread buildModelA = new Thread(() -> {
				modelAGraph = createModelAGraph();
				barrier.countDown();
			});
		buildModelA.start();
		
		// Model B:
		Thread buildModelB = new Thread(() -> {
				modelBGraph = createModelBGraph();
				barrier.countDown();
			});
		buildModelB.start();

		// Full lifting graph:
		if (!buildGraphPerRule) {
			fullEGraph = createSingleEGraph();
		}
		
		// Join threads:
		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return The model graph which contains model/resource A (and import).
	 */
	public EGraph getModelAGraph() {
		return modelAGraph;
	}
	
	/**
	 * @return The model graph which contains model/resource A (and import).
	 */
	public EGraph getModelBGraph() {
		return modelBGraph;
	}

	/**
	 * Creates a dynamic graph view for a given recognition rule.
	 * 
	 * @param rr
	 *            The Recognition-Rule to execute.
	 * @return The working graph.
	 */
	public EGraph createLiftingGraph(Rule rr, RecognitionRuleBlueprint blueprint) {
		assert ((rr != null) && (blueprint != null)) : "Recognition rule and its blueprint can not be null!";
		
		if (buildGraphPerRule) {
			return new LiftingGraphProxy(rr, blueprint, modelAGraph, modelBGraph, liftingGraphDomainMap);
		}
		return fullEGraph;
	}

	/**
	 * Creates a working graph for model/resource A
	 * 
	 * @return The working graph.
	 */
	protected EGraph createModelAGraph() {

		// Initialize Henshin graph:
		EGraph graph = new EGraphImpl();

		// Build Henshin graph (filter duplicated):
		Set<EObject> rootObjects = new HashSet<EObject>();

		// Add model A to graph:
		for (EObject rootObj : difference.getModelA().getContents()) {
			rootObjects.add(rootObj);
			LogUtil.log(LogEvent.DEBUG, "[Root] Model A: " + rootObj);
		}

		// Add all resources of resource set A if mode = "complete resource set":
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

		// Add root objects (and all their contents) to graph:
		for (EObject root : rootObjects) {
			graph.add(root);
			
			for (Iterator<EObject> iterator = root.eAllContents(); iterator.hasNext();) {
				EObject eObject = iterator.next();
				graph.add(eObject);
			}
		}
		
		// Add imports to graph:
		if (imports != null) {
			imports.addImportsModelA(graph);
		}

		return graph;
	}
	
	/**
	 * Creates a working graph for model/resource B
	 * 
	 * @return The working graph.
	 */
	protected EGraph createModelBGraph() {

		// Initialize Henshin graph:
		EGraph graph = new EGraphImpl();

		// Build Henshin graph (filter duplicated):
		Set<EObject> rootObjects = new HashSet<EObject>();

		// Add model B to graph:
		for (EObject rootObj : difference.getModelB().getContents()) {
			rootObjects.add(rootObj);
			LogUtil.log(LogEvent.DEBUG, "[Root] Model B: " + rootObj);
		}

		// Add all resources of resource set B if mode = "complete resource set":
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

		// Add root objects (and all their contents) to graph:
		for (EObject root : rootObjects) {
			graph.add(root);
			
			for (Iterator<EObject> iterator = root.eAllContents(); iterator.hasNext();) {
				EObject eObject = iterator.next();
				graph.add(eObject);
			}
		}
		
		// Add imports to graph:
		if (imports != null) {
			imports.addImportsModelB(graph);
		}

		return graph;
	}
	
	/**
	 * Creates a single working graph for all Recognition-Rules.
	 * 
	 * @param imports
	 *            The imports to merge.
	 * @return The working graph.
	 */
	protected EGraph createSingleEGraph() {

		// FIXME: Add full meta model -> optional AVC -> type node in kernel!
		throw new UnsupportedOperationException();
		
//		// Initialize Henshin graph
//		EGraph graph = new EGraphImpl();
//
//		// Build Henshin graph (filter duplicated)
//		Set<EObject> rootObjects = new HashSet<EObject>();
//
//		// Add Difference-Model to graph
//		rootObjects.add(difference);
//		LogUtil.log(LogEvent.DEBUG, "[Root] Difference: " + difference);
//
////		// Add Ecore to the graph
////		rootObjects.add(EcorePackage.eINSTANCE);
////		LogUtil.log(LogEvent.DEBUG, "[Root] Ecore: " + EcorePackage.eINSTANCE);
//
//		// Add the required types from the meta-model to the graph
//		for (Change change : difference.getChanges()) {
//			EStructuralFeature type = null;
//			if (change instanceof AddReference) {
//				type = ((AddReference) change).getType();
//			}
//			if (change instanceof RemoveReference) {
//				type = ((RemoveReference) change).getType();
//			}
//			if (type != null) {
//				boolean added = graph.add(type);
//				if (added)
//					LogUtil.log(LogEvent.DEBUG, "       Metamodel: " + type);
//			}
//		}
//
//		// Add model A to graph
//		for (EObject rootObj : difference.getModelA().getContents()) {
//			rootObjects.add(rootObj);
//			LogUtil.log(LogEvent.DEBUG, "[Root] Model A: " + rootObj);
//		}
//
//		// Add all resources of resource set A if mode = "complete resource set"
//		if (scope == Scope.RESOURCE_SET) {
//			for (Resource r : difference.getModelA().getResourceSet().getResources()) {
//				if (r == difference.getModelA()) {
//					continue;
//				}
//				for (EObject rootObj : r.getContents()) {
//					rootObjects.add(rootObj);
//					LogUtil.log(LogEvent.DEBUG, "[Root] Res.Set A: " + rootObj);
//				}
//			}
//		}
//
//		// Add model B to graph
//		for (EObject rootObj : difference.getModelB().getContents()) {
//			rootObjects.add(rootObj);
//			LogUtil.log(LogEvent.DEBUG, "[Root] Model B: " + rootObj);
//		}
//
//		// Add all resources of resource set B if mode = "complete resource set"
//		if (scope == Scope.RESOURCE_SET) {
//			for (Resource r : difference.getModelB().getResourceSet().getResources()) {
//				if (r == difference.getModelB()) {
//					continue;
//				}
//				for (EObject rootObj : r.getContents()) {
//					rootObjects.add(rootObj);
//					LogUtil.log(LogEvent.DEBUG, "[Root] Res.Set B: " + rootObj);
//				}
//			}
//		}
//
//		// Add root objects (and all their contents) to graph
//		for (EObject root : rootObjects) {
//			addEObjectToGraph(root, graph);
//			for (Iterator<EObject> iterator = root.eAllContents(); iterator.hasNext();) {
//				EObject eObject = iterator.next();
//				addEObjectToGraph(eObject, graph);
//			}
//		}
//
//		// Add imports to graph
//		if (imports != null) {
//			
//			// A:
//			for (Iterator<EObject> iterator = imports.getIteratorImportsModelA(); iterator.hasNext();) {
//				EObject eObject = (EObject) iterator.next();
//				addEObjectToGraph(eObject, graph);
//			}
//			
//			// B:
//			for (Iterator<EObject> iterator = imports.getIteratorImportsModelB(); iterator.hasNext();) {
//				EObject eObject = (EObject) iterator.next();
//				addEObjectToGraph(eObject, graph);
//			}
//		}
//		return graph;
	}
	
	/**
	 * Adds the EObject and the types of all attribute of the EObject's class.
	 * This is due to the fact that we can never be sure which attribute types
	 * are needed in the graph, due to optional AVCs with a reference to its
	 * attribute type which is in the kernel node of a RR.
	 * 
	 * @param object
	 *            The object to add.
	 * @param graph
	 *            The working graph.
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
}

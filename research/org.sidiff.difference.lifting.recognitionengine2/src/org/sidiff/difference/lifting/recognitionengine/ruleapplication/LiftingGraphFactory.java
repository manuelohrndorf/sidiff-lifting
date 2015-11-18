package org.sidiff.difference.lifting.recognitionengine.ruleapplication;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.technical.ModelImports;

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
	 * Constructs a new graph factory for the given difference and the set of imported eObjects.
	 * 
	 * @param liftingGraphDomainMap
	 *            The corresponding difference index knowing the low-level changes per type.
	 * @param imports
	 *            Imported objects that must be added to the Henshin graph.
	 */
	public LiftingGraphFactory(LiftingGraphDomainMap liftingGraphDomainMap, ModelImports imports, Scope scope) {
		this.difference = liftingGraphDomainMap.getDifference();
		this.imports = imports;
		this.scope = scope;
		
		modelAGraph = createModelAGraph();
		modelBGraph = createModelBGraph();
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
		
		return new LiftingGraphProxy(rr, blueprint, modelAGraph, modelBGraph, liftingGraphDomainMap);
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
		imports.addImportsModelA(graph);

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
		imports.addImportsModelB(graph);

		return graph;
	}
}

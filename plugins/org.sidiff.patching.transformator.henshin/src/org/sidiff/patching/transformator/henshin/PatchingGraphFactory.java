package org.sidiff.patching.transformator.henshin;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.sidiff.patching.settings.ExecutionMode;
import org.silift.common.util.emf.ExternalReference;
import org.silift.common.util.emf.ExternalReferenceCalculator;
import org.silift.common.util.emf.ExternalReferenceContainer;
import org.silift.common.util.emf.Scope;

/**
 * Factory that creates a Henshin graph which contains all objects (and only
 * these objects) that are needed in order to apply a patch to a given target
 * model.
 * 
 * @author kehrer
 */
public class PatchingGraphFactory {

	/**
	 * The target model to which a patch shall be applied.
	 */
	private Resource targetResource;

	/**
	 * The execution mode (interactive or batch).
	 */
	private ExecutionMode executionMode;

	/**
	 * The resource scope (single resource or complete resource set)
	 */
	private Scope scope;

	/**
	 * Constructor:
	 * 
	 * @param targetResource
	 *            The target model to which a patch shall be applied.
	 * @param executionMode
	 *            The execution mode of the patch application (interactive or
	 *            batch).
	 */
	public PatchingGraphFactory(Resource targetResource, ExecutionMode executionMode, Scope scope) {
		super();
		this.targetResource = targetResource;
		this.executionMode = executionMode;
		this.scope = scope;
	}

	/**
	 * Creates a Henshin graph optimized to patch application. Note that in
	 * {@link ExecutionMode#INTERACTIVE} we return a complete graph because we
	 * do not know which objects will be selected as arguments by the user.
	 * 
	 * @return
	 */
	public EGraph createEGraph() {
		EGraph graph = null;

		if (executionMode.equals(ExecutionMode.INTERACTIVE)) {
			graph = new EGraphImpl(targetResource);

		} else {
			// Do not use default EGraph constructor
			// Fill graph manually for better runtime,
			// as no transitive closure is computed
			graph = new EGraphImpl();

			// Add objects of target resource
			for (Iterator<EObject> iterator = targetResource.getAllContents(); iterator.hasNext();) {
				EObject eObject = iterator.next();
				graph.add(eObject);
			}

			// Add externally referenced objects
			ExternalReferenceCalculator externalRefCalculator = new ExternalReferenceCalculator();
			ExternalReferenceContainer externalRefs = externalRefCalculator.calculate(targetResource, scope);
			for (ExternalReference extRef : externalRefs.getRegistryReferences()) {
				graph.add(extRef.getTargetObject());
			}
			for (ExternalReference extRef : externalRefs.getResourceSetReferences()) {
				graph.add(extRef.getTargetObject());
			}
		}

		return graph;
	}
}

package org.sidiff.patching.transformator.henshin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.common.extension.AbstractTypedExtension;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.editrule.rulebase.EditRule;
import org.sidiff.editrule.rulebase.Parameter;
import org.sidiff.editrule.rulebase.ParameterDirection;
import org.sidiff.patching.ExecutionMode;
import org.sidiff.patching.exceptions.OperationNotExecutableException;
import org.sidiff.patching.exceptions.OperationNotUndoableException;
import org.sidiff.patching.exceptions.ParameterMissingException;
import org.sidiff.patching.transformation.ITransformationEngine;

/**
 * Transformation Engine based on calling Henshin Transformator.
 * 
 * @author Dennis Koch, kehrer, reuling
 * 
 */
public class HenshinTransformationEngineImpl extends AbstractTypedExtension implements ITransformationEngine {

	/**
	 * The target resource on which the patch shall be applied.
	 */
	private Resource targetResource;

	/**
	 * The execution mode (interactive or batch).
	 */
	private ExecutionMode executionMode;
	
	/**
	 * The comparison mode (single resource or complete resource set)
	 */
	private Scope scope;
	
	/**
	 * The Henshin Graph that contains the target resource on which the patch
	 * shall be applied.
	 */
	private EGraph graph;
	
	/**
	 * A Map which contains all executed operation invocations and their
	 * corresponding unit application
	 */
	private Map<OperationInvocation,UnitApplication> executedOperations = new HashMap<>();

	/**
	 * Root objects initially contained by the Henshin graph.
	 */
	private Collection<EObject> initialGraphRoots;

	@Override
	public void init(Resource targetResource, ExecutionMode executionMode, Scope scope) {
		this.targetResource = targetResource;
		this.executionMode = executionMode;
		this.scope = scope;
		
		// Create graph
		PatchingGraphFactory graphFactory = new PatchingGraphFactory(targetResource, executionMode, scope);
		graph = graphFactory.createEGraph();
		
		// Store initial graph roots
		initialGraphRoots = new ArrayList<>();
		for (EObject obj : graph.getRoots()) {
			initialGraphRoots.add(obj);
		}
	}

	@Override
	public Map<ParameterBinding, Object> execute(OperationInvocation operationInvocation, Map<ParameterBinding, Object> inputParameters)
			throws ParameterMissingException, OperationNotExecutableException {

		if(graph == null) {
			throw new IllegalStateException("HenshinTransformationEngine has not been initialized (init must be called prior)");
		}

		EditRule editRule = operationInvocation.resolveEditRule();
		String operationName = editRule.getExecuteModule().getName();
		LogUtil.log(LogEvent.NOTICE, "Executing operation " + operationName);

		// hard binding between operation and henshin should be splitted
		// (see old henshin executor in repository)
		Unit unit = editRule.getExecuteMainUnit();
		Engine engine = new EngineImpl();
		UnitApplication application = new UnitApplicationImpl(engine);
		application.setEGraph(graph);
		application.setUnit(unit);

		// potentially missing parameters
		List<String> missingParameters = new ArrayList<>();
		for (Parameter ruleParameter : editRule.getParameters()) {
			if (ruleParameter.getDirection() == ParameterDirection.IN) {
				missingParameters.add(ruleParameter.getName());
			}
		}
		
		// Setting the parameter values
		for (ParameterBinding binding : inputParameters.keySet()) {
			Object argument = inputParameters.get(binding);
			application.setParameterValue(binding.getFormalName(), argument);
			missingParameters.remove(binding.getFormalName());
		}

		if (!missingParameters.isEmpty()) {
			throw new ParameterMissingException(operationName, missingParameters);
		}

		Map<ParameterBinding, Object> outputMap = new HashMap<>();
		if (application.execute(null)) {
			//Save application for "undo" purposes
			executedOperations.put(operationInvocation, application);
			for (ParameterBinding binding : operationInvocation.getParameterBindings()) {
				if (binding.getFormalParameter().getDirection() != ParameterDirection.OUT
						|| !(binding instanceof ObjectParameterBinding)) {
					continue;
				}

				String formalName = binding.getFormalName();
				Object parameterValue = application.getResultParameterValue(formalName);
				EObject eObject = ((ObjectParameterBinding)binding).getActualB();
				String id = "";
				//FIXME (cpietsch 17.02.2015) prototypical handling of symbolic links with uuids in order to preserve the original object id
				try {
					id = (String)eObject.eGet(eObject.eClass().getEStructuralFeature("uuid"));
				} catch (Exception e){
					LogUtil.log(LogEvent.WARNING, eObject + " has no uuid attribute");
				}
				if(id.isEmpty()){
					id = EMFUtil.getXmiId(eObject);
				}
				EMFUtil.setXmiId((EObject)parameterValue, id);
				outputMap.put(binding, parameterValue);
			}
		} else {
			throw new OperationNotExecutableException(operationName);
		}

		// TODO: we don't need to call this after each operation invocation
		// once when path is finished would be enough.
		synchronizeResourceWithGraph();

		return outputMap;
	}
	
	@Override
	public void undo(OperationInvocation operationInvocation)
			throws OperationNotUndoableException {
		
		String operationName = operationInvocation.resolveEditRule().getExecuteModule().getName();
		LogUtil.log(LogEvent.NOTICE, "Undoing operation " + operationName);
		
		//Get corresponding unit application
		UnitApplication application = executedOperations.get(operationInvocation);
		
		//Revert the operation
		try {
			boolean reverted = application.undo(null);
			if (reverted){
				executedOperations.remove(operationInvocation);
				LogUtil.log(LogEvent.NOTICE, "Successfully undone!" );
			}else{
				throw new OperationNotUndoableException(operationName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationNotUndoableException(operationName);
		}
		
		// TODO: we don't need to call this after each operation invocation
		// once when path is finished would be enough.
		synchronizeResourceWithGraph();
	}

	private void synchronizeResourceWithGraph() {
		for (Iterator<EObject> iterator = initialGraphRoots.iterator(); iterator.hasNext();) {
			EObject resourceObj = iterator.next();
			if (!graph.contains(resourceObj)) {
				// remove from resource
				targetResource.getContents().remove(resourceObj);
			}
		}

		for (Iterator<EObject> iterator = graph.getRoots().iterator(); iterator.hasNext();) {
			EObject graphObj = iterator.next();
			if (!initialGraphRoots.contains(graphObj)) {
				// add to resource
				if (!targetResource.getContents().contains(graphObj)) {
					targetResource.getContents().add(graphObj);
				}
			}
		}
	}
	
	public ExecutionMode getExecutionMode() {
		return executionMode;
	}

	public Scope getScope() {
		return scope;
	}
}

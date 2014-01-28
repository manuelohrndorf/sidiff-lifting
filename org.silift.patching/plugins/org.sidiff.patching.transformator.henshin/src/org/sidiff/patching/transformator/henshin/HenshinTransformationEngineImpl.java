package org.sidiff.patching.transformator.henshin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.rulebase.EditRule;
import org.sidiff.difference.rulebase.Parameter;
import org.sidiff.difference.rulebase.ParameterDirection;
import org.sidiff.patching.PatchEngine.ExecutionMode;
import org.sidiff.patching.exceptions.OperationNotExecutableException;
import org.sidiff.patching.exceptions.OperationNotUndoableException;
import org.sidiff.patching.exceptions.ParameterMissingException;
import org.sidiff.patching.transformation.ITransformationEngine;
import org.silift.common.util.emf.Scope;

/**
 * Transformation Engine based on calling Henshin Transformator.
 * 
 * @author Dennis Koch, kehrer, reuling
 * 
 */
public class HenshinTransformationEngineImpl implements ITransformationEngine {

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
	private Map<OperationInvocation,UnitApplication> executedOperations = new HashMap<OperationInvocation, UnitApplication>();

	/**
	 * Root objects initially contained by the Henshin graph.
	 */
	private Collection<EObject> initialGraphRoots;

	@Override
	public void init(Resource targetResource, ExecutionMode executionMode, Scope scope) {
		this.targetResource = targetResource;
		this.executionMode = executionMode;
		
		// Create graph
		PatchingGraphFactory graphFactory = new PatchingGraphFactory(targetResource, executionMode, scope);
		graph = graphFactory.createEGraph();
		
		// Store initial graph roots
		initialGraphRoots = new LinkedList<EObject>();
		for (EObject obj : graph.getRoots()) {
			initialGraphRoots.add(obj);
		}
	}

	@Override
	public Map<ParameterBinding, Object> execute(OperationInvocation operationInvocation, Map<ParameterBinding, Object> inputParameters)
			throws ParameterMissingException, OperationNotExecutableException {
		assert (graph != null) : "Model not set and therefore no EGraph!";
		String operationName = operationInvocation.getEditRule().getExecuteModule().getName();
		LogUtil.log(LogEvent.NOTICE, "Executing operation " + operationName);

		// hard binding between operation and henshin should be splitted
		// (see old henshin executor in repository)
		EditRule editRule = operationInvocation.resolveEditRule();
		// =====
		// FIXME: Check-Dangling WORKAROUND
		danglingConstraintWorkaround(editRule);
		// =====
		Unit unit = editRule.getExecuteMainUnit();
		Engine engine = new EngineImpl();
		UnitApplication application = new UnitApplicationImpl(engine);
		application.setEGraph(graph);
		application.setUnit(unit);

		// potentially missing parameters
		List<String> missingParameters = new ArrayList<String>();
		for (Parameter ruleParameter : editRule.getParameters()) {
			if (ruleParameter.getDirection() == ParameterDirection.IN) {
				missingParameters.add(ruleParameter.getName());
			}
		}
		
		// Setting the parameter values
		for (ParameterBinding binding : inputParameters.keySet()) {
			Object argument = getArgument(binding, inputParameters);
			application.setParameterValue(binding.getFormalName(), argument);
			missingParameters.remove(binding.getFormalName());
		}

		if (!missingParameters.isEmpty()) {
			throw new ParameterMissingException(operationName, missingParameters.toArray(new String[missingParameters
					.size()]));
		}

		Map<ParameterBinding, Object> outputMap = new HashMap<ParameterBinding, Object>();
		if (application.execute(null)) {
			//Save application for "undo" purposes
			executedOperations.put(operationInvocation, application);
			for (ParameterBinding binding : operationInvocation.getParameterBindings()) {
				if (binding.getFormalParameter().getDirection() == ParameterDirection.OUT) {
					if (binding instanceof ObjectParameterBinding) {
						String formalName = binding.getFormalName();
						Object parameterValue = application.getResultParameterValue(formalName);
						outputMap.put(binding, (EObject) parameterValue);
					}
				}
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
		
		String operationName = operationInvocation.getEditRule().getExecuteModule().getName();
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

	/**
	 * On demand, a usual list of arguments is converted into a
	 * ParameterValueList which is used by our own Henshin implementation which
	 * handles parameter lists. All other kinds of parameters, i.e. single
	 * object parameters and value parameters, are just returned as they are.
	 * 
	 * @param binding
	 * @param inputParameters
	 * @return
	 */
	private Object getArgument(ParameterBinding binding, Map<ParameterBinding, Object> inputParameters) {
		Object argument = inputParameters.get(binding);
			return argument;
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

	// ================================================================
	/**
	 * Dangling constraint WORKAROUND:
	 * 
	 * Problem:<br/>
	 * Das neue Konzept der geschachtelten Regeln weist gegenüber den früheren
	 * AmalgamationUnits an einer Stelle semantisch ab: Von Kernel-Regeln wird
	 * die Dangling-Bedingung geprüft, ohne daß die Effekte der Multi-Regeln
	 * berücksichtigt werden. Dabei könnten ja gerade durch die Multi-Regeln
	 * entscheidende Kanten gelöscht werden, so dass die Dangling Bedingung
	 * erfüllt wäre.
	 * 
	 * Lösung<br/>
	 * Kernel-Regeln (also Regeln mit eingebetteten Multi-Regeln) setzen wir für
	 * die Patch-Ausführung pauschal auf checkDangling = false.
	 */
	private void danglingConstraintWorkaround(EditRule editRule) {
		for (Rule r : editRule.getExecuteModule().getRules()) {
			danglingConstraintWorkaround(r);
		}
	}

	private void danglingConstraintWorkaround(Rule rule) {
		if (!rule.getMultiRules().isEmpty()) {
			// its a kernel rule
			rule.setCheckDangling(false);
		}

		for (Rule mr : rule.getMultiRules()) {
			danglingConstraintWorkaround(mr);
		}
	}
	// ================================================================

}

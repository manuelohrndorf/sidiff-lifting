package org.sidiff.patching.transformator.henshin.impl;

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
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.ParameterList;
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
import org.sidiff.difference.rulebase.ParameterKind;
import org.sidiff.patching.exceptions.OperationNotExecutableException;
import org.sidiff.patching.exceptions.ParameterMissingException;
import org.sidiff.patching.transformator.henshin.HenshinTransformationEngine;

/**
 * Transformation Engine based on calling Henshin Transformator.
 * 
 * @author Dennis Koch, kehrer , reuling
 * 
 */
public class HenshinTransformationEngineImpl implements HenshinTransformationEngine {

	/**
	 * The target resource on which the patch shall be applied.
	 */
	private Resource resource;

	/**
	 * The Henshin Graph that contains the target resource on which the patch
	 * shall be applied.
	 */
	private EGraph graph;

	/**
	 * Root objects initially contained by the Henshin graph.
	 */
	private Collection<EObject> initialGraphRoots;
	
	@Override
	public void setResource(Resource resource) {
		this.resource = resource;
		// Do not use default EGraph constructor
		// Fill graph manually for better runtime,
		// as no transitive closure is computed
		graph = new EGraphImpl(resource);
		
		//TODO fix this crazy motherfucker
		/*
		
		for (Iterator<EObject> iterator = resource.getAllContents(); iterator
				.hasNext();) {
			EObject obj = (EObject) iterator.next();
			graph.add(obj);

		} */
		
		initialGraphRoots = new LinkedList<EObject>();
		for (EObject obj : graph.getRoots()) {
			initialGraphRoots.add(obj);
		}
	}

	@Override
	public Map<String, Object> execute(OperationInvocation operationInvocation, Map<String, Object> inputParameters)
			throws ParameterMissingException, OperationNotExecutableException {
		assert (graph != null) : "Model not set and therefore no EGraph!";
		String operationName = operationInvocation.getChangeSet().getName();
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

		// Setting the parameter values
		for (String str : inputParameters.keySet()) {
			Object argument = getArgument(str, inputParameters);
			application.setParameterValue(str, argument);
		}

		List<String> missingParameters = new ArrayList<String>();
		for (Parameter ruleParameter : editRule.getParameters()) {
			if (ruleParameter.getDirection() == ParameterDirection.IN) {
				String parameterName = ruleParameter.getName();
				Object object = inputParameters.get(parameterName);
				if (object == null && ruleParameter.getKind() == ParameterKind.OBJECT) {
					missingParameters.add(parameterName);
				}
			}
		}
		if (!missingParameters.isEmpty()) {
			throw new ParameterMissingException(operationName, missingParameters.toArray(new String[missingParameters
					.size()]));
		}

		Map<String, Object> outputMap = new HashMap<String, Object>();
		if (application.execute(null)) {
			for (ParameterBinding binding : operationInvocation.getParameterBindings()) {
				if (binding.getFormalParameter().getDirection() == ParameterDirection.OUT) {
					if (binding instanceof ObjectParameterBinding) {
						String formalName = binding.getFormalName();
						Object parameterValue = application.getResultParameterValue(formalName);
						outputMap.put(formalName, (EObject) parameterValue);
					}
				}
			}
		} else {
			throw new OperationNotExecutableException(operationName);
		}
		
		//TODO: we don't need to call this after each operation invocation
		//      once when path is finished would be enough.
		synchronizeResourceWithGraph();
		
		return outputMap;
	}

	/**
	 * On demand, a usual list of arguments is converted into a
	 * ParameterValueList which is used by our own Henshin implementation which
	 * handles parameter lists. All other kinds of parameters, i.e. single
	 * object parameters and value parameters, are just returned as they are.
	 * 
	 * @param formal
	 * @param inputParameters
	 * @return
	 */
	private Object getArgument(String formal, Map<String, Object> inputParameters) {
		Object argument = inputParameters.get(formal);
		if (argument instanceof List) {
			List commonList = (List) argument;
			ParameterList pvl = new ParameterList();
			for (Object object : commonList) {
				pvl.addValue(object);
			}
			return pvl;

		} else {
			return argument;
		}
	}

	private void synchronizeResourceWithGraph() {				
		for (Iterator<EObject> iterator = initialGraphRoots.iterator(); iterator.hasNext();) {
			EObject resourceObj = iterator.next();
			if (!graph.contains(resourceObj)){
				//remove from resource
				resource.getContents().remove(resourceObj);
			}			
		}
		
		for (Iterator<EObject> iterator = graph.getRoots().iterator(); iterator.hasNext();) {
			EObject graphObj = iterator.next();
			if (!initialGraphRoots.contains(graphObj)){
				//add to resource
				if (!resource.getContents().contains(graphObj)){
					resource.getContents().add(graphObj);
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

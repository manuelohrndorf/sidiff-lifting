package org.sidiff.patching.transformator.henshin.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.UnitApplication;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.interpreter.impl.UnitApplicationImpl;
import org.eclipse.emf.henshin.model.Unit;
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
 * @author Dennis Koch
 * 
 */
public class HenshinTransformationEngineImpl implements HenshinTransformationEngine {
	private static final Logger LOGGER = Logger.getLogger(HenshinTransformationEngineImpl.class.getName());
	private EGraph graph;

	@Override
	public void setResource(Resource resource) {
		graph = new EGraphImpl(resource);
	}

	@Override
	public Map<String, EObject> execute(OperationInvocation operationInvocation, Map<String, Object> inputParameters)
			throws ParameterMissingException, OperationNotExecutableException {
		assert (graph != null) : "Model not set and therefor no EGraph!";
		String operationName = operationInvocation.getChangeSet().getName();
		LOGGER.log(Level.FINE, "Executing operation " + operationName);

		// hard binding between operation and henshin should be splited
		// (see old henshin executor in repository)
		EditRule editRule = operationInvocation.resolveEditRule();
		Unit unit = editRule.getExecuteMainUnit();
		Engine engine = new EngineImpl();
		UnitApplication application = new UnitApplicationImpl(engine);
		application.setEGraph(graph);
		application.setUnit(unit);
		
		//Setting the parameter values
		for(String str : inputParameters.keySet()){
			application.setParameterValue(str, inputParameters.get(str));
		}

		List<String> missingParameters = new ArrayList<String>();
		for (Parameter ruleParameter : editRule.getParameters()) {
			if (ruleParameter.getDirection() == ParameterDirection.IN) {
				String parameterName = ruleParameter.getName();
				Object object = inputParameters.get(parameterName);
				if (object == null && ruleParameter.getKind() == ParameterKind.OBJECT) {
					missingParameters.add(parameterName);
				}
				application.setParameterValue(parameterName, object);
			}
		}
		if (!missingParameters.isEmpty()) {
			throw new ParameterMissingException(operationName, missingParameters.toArray(new String[missingParameters.size()]));
		}

		Map<String, EObject> outputMap = new HashMap<String, EObject>();
		if (application.execute(null)) {
			for (ParameterBinding binding : operationInvocation.getParameterBindings()) {
				if (binding.getFormalParameter().getDirection() == ParameterDirection.OUT) {
					if (binding instanceof ObjectParameterBinding) {
						String formalName = binding.getFormalName();
						Object parameterValue = application.getResultParameterValue(formalName);
						if (parameterValue instanceof EObject) {
							outputMap.put(formalName, (EObject) parameterValue);
						} else {
							LOGGER.log(Level.SEVERE, "OutputValue is not an EObject!");
						}
					}
				}
			}
		} else {
			throw new OperationNotExecutableException(operationName);
		}
		return outputMap;
	}
}

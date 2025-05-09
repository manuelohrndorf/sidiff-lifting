package org.sidiff.difference.asymmetric.paramretrieval;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.common.logging.LogEvent;
import org.sidiff.common.logging.LogUtil;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.AsymmetricFactory;
import org.sidiff.difference.asymmetric.DependencyContainer;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ParameterMapping;
import org.sidiff.editrule.rulebase.Parameter;
import org.sidiff.editrule.rulebase.ParameterDirection;

/**
 * Analyzes a given asymmetric differences and maps out parameters that create
 * objects to the in parameters that are using these created objects.<br/>
 * After all parameters are mapped, the asymmetric difference needs no more
 * access to the former model B from which it has been obtained.
 * 
 * @author kehrer
 */
public class ParameterMapper {

	/**
	 * The asymmetric difference
	 */
	private AsymmetricDifference asymDiff;

	/**
	 * ObjectParameterBindings that are only referencing objects in model B AND
	 * are IN-Parameters
	 */
	private Set<ObjectParameterBinding> paramBindings_IN_B;

	/**
	 * Mapping: addedEObject -> the OUT ParamBinding which references
	 * addedEObject
	 */
	private Map<EObject, ObjectParameterBinding> paramBindings_OUT_B;

	/**
	 * Creates a new ParameterMapper
	 * 
	 * @param asymDiff
	 */
	public ParameterMapper(AsymmetricDifference asymDiff) {
		this.asymDiff = Objects.requireNonNull(asymDiff, "asymDiff is null");
	}

	public void mapParameters() {
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");
		LogUtil.log(LogEvent.NOTICE, "---------------------- MAP PARAMETERS ----------------------");
		LogUtil.log(LogEvent.NOTICE, "------------------------------------------------------------");

		init();

		for (ObjectParameterBinding bindingIn : paramBindings_IN_B) {
			ObjectParameterBinding bindingOut = paramBindings_OUT_B.get(bindingIn.getActualB());

			// FIXME[resource set internal references]: Filter e.g. PrimitiveTypes in UML
			if (bindingOut != null) {
				OperationInvocation opIn = (OperationInvocation) bindingIn.eContainer();
				OperationInvocation opOut = (OperationInvocation) bindingOut.eContainer();
				assert existsDependency(opIn, opOut) : opIn + " must have a dependency to " + opOut;

				// create mapping and add it to difference
				ParameterMapping mapping = AsymmetricFactory.eINSTANCE.createParameterMapping();
				mapping.setSource(bindingOut);
				mapping.setTarget(bindingIn);
				asymDiff.getParameterMappings().add(mapping);
			}
		}
	}

	private void init() {
		paramBindings_IN_B = new HashSet<>();
		paramBindings_OUT_B = new HashMap<>();

		for (OperationInvocation operationInvocation : asymDiff.getOperationInvocations()) {
			for (ParameterBinding parameterBinding : operationInvocation.getParameterBindings()) {
				// We are only interested in object parameter bindings
				if (parameterBinding instanceof ObjectParameterBinding) {
					ObjectParameterBinding objParameterBinding = (ObjectParameterBinding) parameterBinding;

					// FIXME[resource set internal references]: Filter e.g. PrimitiveTypes in UML

					// only in B, i.e.: actualA == null && actualB != null
					if (objParameterBinding.getActualA() == null
							&& objParameterBinding.getActualB() != null) {

						Parameter formalParameter = parameterBinding.getFormalParameter();
						if (formalParameter.getDirection() == ParameterDirection.IN) {
							paramBindings_IN_B.add(objParameterBinding);
						} else if (formalParameter.getDirection() == ParameterDirection.OUT) {
							paramBindings_OUT_B.put(objParameterBinding.getActualB(), objParameterBinding);
						}
					}
				}
			}
		}
	}

	/**
	 * Checks whether there is a dependency from the given source to the given
	 * target OperationInvocation.
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	private static boolean existsDependency(OperationInvocation source, OperationInvocation target) {
		for (DependencyContainer dep : source.getOutgoing()) {
			if (dep.getTarget() == target) {
				return true;
			}
		}
		return false;
	}
}

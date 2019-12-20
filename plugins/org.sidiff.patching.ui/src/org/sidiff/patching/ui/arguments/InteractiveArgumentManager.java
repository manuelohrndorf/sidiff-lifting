package org.sidiff.patching.ui.arguments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFMetaAccess;
import org.sidiff.difference.asymmetric.MultiParameterBinding;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.patching.ExecutionMode;
import org.sidiff.patching.arguments.AbstractMatcherBasedArgumentManager;
import org.sidiff.patching.arguments.MultiArgumentWrapper;
import org.sidiff.patching.arguments.ObjectArgumentWrapper;
import org.sidiff.patching.arguments.ValueArgumentWrapper;

/**
 * An implementation of {@link IArgumentManager} that subclasses the
 * {@link AbstractMatcherBasedArgumentManager} which internally delegates the
 * computation of the initial correspondences to a SiLift {@link IMatcher}.
 * 
 * Particularly, in this {@link InteractiveArgumentManager} argument manager,
 * corrections to the correspondences (e.g. in interactive mode) are managed.
 * 
 * @author kehrer
 */
public class InteractiveArgumentManager extends AbstractMatcherBasedArgumentManager {

	public InteractiveArgumentManager() {
		// default constructor for extension point
	}

	@Override
	public void setArgument(ValueParameterBinding binding, Object value) {
		ValueArgumentWrapper argWrapper = (ValueArgumentWrapper) getArgumentResolutions().get(binding);
		argWrapper.setValue((String) value);
	}

	@Override
	public Map<Resource, Collection<EObject>> getPotentialArguments(ObjectParameterBinding binding) {
		EObject originObject = binding.getActualA();
		if(originObject == null) {
			return Collections.emptyMap();
		}
		Map<Resource, Collection<EObject>> res = new HashMap<>();

		// from target model.
		{
			List<EObject> args = new ArrayList<>();
			addPossibleArgument(args, getTargetModel(), originObject);
			res.put(getTargetModel(), args);
		}

		// from package registry
		for (Resource r : getPackageRegistryResources()) {
			List<EObject> args = new ArrayList<>();
			addPossibleArgument(args, r, originObject);
			res.put(r, args);
		}

		// from ResourceSet
		for (Resource r : getResourceSetResources()) {
			List<EObject> args = new ArrayList<>();
			addPossibleArgument(args, r, originObject);
			res.put(r, args);
		}

		return res;
	}

	private void addPossibleArgument(List<EObject> args, Resource resource, EObject originObject) {
		// Do not include elements defined in Ecore Meta-model itself
		// (except EDataTypes).
		// They would only be needed if the Ecore-Metamodel itself shall be
		// patched (most likely, we will never do that).
		if (resource.getURI().toString().equals(EcorePackage.eNS_URI) && !(originObject instanceof EDataType)) {
			return;
		}

		// Otherwise, we only check type compatibility
		for (Iterator<EObject> it = resource.getAllContents(); it.hasNext();) {
			EObject obj = it.next();
			if (EMFMetaAccess.isAssignableTo(obj.eClass(), originObject.eClass())) {
				args.add(obj);
			}
		}
	}

	@Override
	public void resetArgumentResolution(ObjectParameterBinding binding) {
		ObjectArgumentWrapper objectArgWrapper = (ObjectArgumentWrapper) getArgumentResolutions().get(binding);
		objectArgWrapper.resetResolution();
	}

	@Override
	public void removeTargetObject(EObject targetObject) {
		for (ParameterBinding b : getArgumentResolutions().keySet()) {
			if (b instanceof ObjectParameterBinding) {
				ObjectArgumentWrapper arg = (ObjectArgumentWrapper) getArgumentResolutions().get(b);
				if (arg.isResolved() && arg.getTargetObject() == targetObject) {
					arg.resetResolution();
				}
			}
			if (b instanceof MultiParameterBinding) {
				MultiArgumentWrapper multiArg = (MultiArgumentWrapper) getArgumentResolutions().get(b);
				multiArg.removeTargetObject(targetObject);
			}
		}
	}

	@Override
	public void addTargetObject(EObject targetObject) {
		for (ParameterBinding b : getArgumentResolutions().keySet()) {
			if (b instanceof ObjectParameterBinding) {
				ObjectArgumentWrapper arg = (ObjectArgumentWrapper) getArgumentResolutions().get(b);
				if (!arg.isResolved() && arg.getTargetObject() == targetObject) {
					arg.restoreResolution();
				}
			}
			// We do not need to restore multi argument resolutions because they
			// have been deleted.
		}
	}

	@Override
	public String getKey() {
		return this.getClass().getName();
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public ExecutionMode getExecutionMode() {
		return ExecutionMode.INTERACTIVE;
	}
}

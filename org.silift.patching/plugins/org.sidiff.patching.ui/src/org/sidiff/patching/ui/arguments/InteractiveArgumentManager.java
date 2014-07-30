package org.sidiff.patching.ui.arguments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.MultiParameterBinding;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ParameterMapping;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.difference.matcher.IMatcher;
import org.sidiff.difference.symmetric.Correspondence;
import org.sidiff.difference.symmetric.SymmetricDifference;
import org.sidiff.difference.symmetric.SymmetricFactory;
import org.sidiff.patching.arguments.ArgumentWrapper;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.arguments.MultiArgumentWrapper;
import org.sidiff.patching.arguments.ObjectArgumentWrapper;
import org.sidiff.patching.arguments.ValueArgumentWrapper;
import org.silift.common.util.access.EMFMetaAccessEx;
import org.silift.common.util.emf.EMFResourceUtil;
import org.silift.common.util.emf.EObjectLocation;
import org.silift.common.util.emf.ExternalReferenceCalculator;
import org.silift.common.util.emf.ExternalReferenceContainer;
import org.silift.common.util.emf.Scope;
import org.silift.modifieddetector.IModifiedDetector;
import org.silift.patching.settings.PatchMode;

/**
 * An implementation of {@link IArgumentManager} that internally delegates the
 * computation of the initial correspondences to a SiLift {@link IMatcher}.
 * Corrections to the correspondences (e.g. in interactive mode) are managed.
 * 
 * @author kehrer
 */
public class InteractiveArgumentManager implements IArgumentManager {

	/**
	 * The SiLift matcher to which the initial correspondence calculation is
	 * delegated to.
	 */
	private IMatcher matcher;

	/**
	 * Reliability threshold.
	 */
	private float minReliability;

	/**
	 * The patch which is to be applied.
	 */
	private AsymmetricDifference patch;

	/**
	 * The origin model.
	 */
	private Resource originModel;

	/**
	 * The target model.
	 */
	private Resource targetModel;

	/**
	 * Correspondences between originModel and targetModel.
	 */
	private SymmetricDifference matching;

	/**
	 * The PackageRegistry resources that seem to be actually used.
	 */
	private Set<Resource> packageRegistryResources;

	/**
	 * The ResourceSet resources that seem to be actually used.
	 */
	private Set<Resource> resourceSetResources;

	/**
	 * The scope (resource only or complete resource set).
	 */
	private Scope scope;

	/**
	 * The patch mode (patching or merging).
	 */
	private PatchMode patchMode;

	/**
	 * The ModifiedDetector to which we delegate when we check if an object of
	 * the origin model has been modified in the target model.
	 */
	private IModifiedDetector modDetector;

	private Map<ParameterBinding, ArgumentWrapper> argumentResolutions;

	public InteractiveArgumentManager(IMatcher matcher) {
		this.matcher = matcher;
	}

	@Override
	public void init(AsymmetricDifference patch, Resource targetModel, Scope scope, PatchMode patchMode) {
		this.patch = patch;
		this.originModel = patch.getOriginModel();
		this.targetModel = targetModel;
		this.scope = scope;
		this.patchMode = patchMode;

		// now we initialize the internal state...

		// do matching
		matching = matcher.createMatching(originModel, targetModel, scope, true);

		// collect referenced registry and ResourceSet resources
		ExternalReferenceCalculator refCalculator = new ExternalReferenceCalculator();
		ExternalReferenceContainer extContainer = refCalculator.calculate(originModel, scope);
		packageRegistryResources = extContainer.getReferencedRegistryModels();
		resourceSetResources = extContainer.getReferencedResourceSetModels();

		// init argument wrappers and provide initial resolutions
		argumentResolutions = new HashMap<ParameterBinding, ArgumentWrapper>();
		for (OperationInvocation invocation : patch.getOperationInvocations()) {
			for (ParameterBinding binding : invocation.getParameterBindings()) {
				if (binding instanceof ObjectParameterBinding) {
					ObjectParameterBinding objBinding = (ObjectParameterBinding) binding;
					ObjectArgumentWrapper arg = new ObjectArgumentWrapper(objBinding, this);
					if (objBinding.getActualA() != null) {
						// try to resolve originObject
						EObject targetObject = resolveOriginObject(objBinding.getActualA());
						if (targetObject != null) {
							arg.resolveTo(targetObject);
						}
					}
					argumentResolutions.put(binding, arg);
				}
				if (binding instanceof ValueParameterBinding) {
					ValueParameterBinding valueParameterBinding = (ValueParameterBinding) binding;
					ValueArgumentWrapper arg = new ValueArgumentWrapper(valueParameterBinding, this);
					arg.setValue(valueParameterBinding.getActual());
					argumentResolutions.put(binding, arg);
				}
				if (binding instanceof MultiParameterBinding) {
					MultiParameterBinding multiParameterBinding = (MultiParameterBinding) binding;
					MultiArgumentWrapper arg = new MultiArgumentWrapper(multiParameterBinding, this);
					for (ObjectArgumentWrapper nestedArg : arg.getNestedWrappers()) {
						ObjectParameterBinding objBinding = nestedArg.getObjectBinding();
						// try to resolve originObject
						EObject targetObject = resolveOriginObject(objBinding.getActualA());
						if (targetObject != null) {
							nestedArg.resolveTo(targetObject);
						}
					}
					argumentResolutions.put(binding, arg);
				}
			}
		}
	}

	@Override
	public void init(AsymmetricDifference patch, Resource targetModel, Scope scope, PatchMode patchMode,
			IModifiedDetector modifiedDetector) {

		init(patch, targetModel, scope, patchMode);
		this.modDetector = modifiedDetector;
	}

	@Override
	public ArgumentWrapper getArgument(ParameterBinding binding) {
		return argumentResolutions.get(binding);
	}

	@Override
	public void setArgument(ValueParameterBinding binding, Object value) {
		ValueArgumentWrapper argWrapper = (ValueArgumentWrapper) argumentResolutions.get(binding);
		argWrapper.setValue((String) value);
	}

	@Override
	public Map<Resource, Collection<EObject>> getPotentialArguments(ObjectParameterBinding binding) {
		EObject originObject = binding.getActualA();
		Map<Resource, Collection<EObject>> res = new HashMap<Resource, Collection<EObject>>();

		// from target model.
		List<EObject> args = new ArrayList<EObject>();
		addPossibleArgument(args, targetModel, originObject);
		res.put(targetModel, args);

		// from package registry
		for (Resource r : packageRegistryResources) {
			args = new ArrayList<EObject>();
			addPossibleArgument(args, r, originObject);
			res.put(r, args);
		}

		// from ResourceSet
		for (Resource r : resourceSetResources) {
			args = new ArrayList<EObject>();
			addPossibleArgument(args, r, originObject);
			res.put(r, args);
		}

		return res;
	}

	private void addPossibleArgument(List<EObject> args, Resource resource, EObject originObject) {
		// Do not include elements defined in Ecore Meta-model itself
		// (ausgenommen EDataTypes).
		// They would only be needed if the Ecore-Metamodel itself shall be
		// patched (we probably will never do that).
		if (resource.getURI().toString().equals(EcorePackage.eNS_URI) && !(originObject instanceof EDataType)) {
			return;
		}

		// Otherwise, we only check type compatibility
		for (Iterator<EObject> it = resource.getAllContents(); it.hasNext();) {
			EObject obj = it.next();
			if (EMFMetaAccessEx.isAssignableTo(obj.eClass(), originObject.eClass())) {
				args.add(obj);
			}
		}
	}

	@Override
	public void addArgumentResolution(ObjectParameterBinding binding, EObject targetObject) {
		ObjectArgumentWrapper objectArgWrapper = (ObjectArgumentWrapper) argumentResolutions.get(binding);
		objectArgWrapper.resolveTo(targetObject);

		// do also resolve target bindings to which binding is mapped
		for (ParameterMapping mapping : binding.getOutgoing()) {
			addArgumentResolution(mapping.getTarget(), targetObject);
		}
	}

	@Override
	public void resetArgumentResolution(ObjectParameterBinding binding) {
		ObjectArgumentWrapper objectArgWrapper = (ObjectArgumentWrapper) argumentResolutions.get(binding);
		objectArgWrapper.resetResolution();
	}

	@Override
	public void removeTargetObject(EObject targetObject) {
		for (ParameterBinding b : argumentResolutions.keySet()) {
			if (b instanceof ObjectParameterBinding) {
				ObjectArgumentWrapper arg = (ObjectArgumentWrapper) argumentResolutions.get(b);
				if (arg.isResolved() && arg.getTargetObject() == targetObject) {
					arg.resetResolution();
				}
			}
			if (b instanceof MultiParameterBinding) {
				MultiArgumentWrapper multiArg = (MultiArgumentWrapper) argumentResolutions.get(b);
				for (Iterator<ObjectArgumentWrapper> iterator = multiArg.getNestedWrappers().iterator(); iterator
						.hasNext();) {
					ObjectArgumentWrapper nestedArg = iterator.next();
					if (nestedArg.isResolved() && nestedArg.getTargetObject() == targetObject) {
						iterator.remove();
					}
				}
			}
		}
	}

	@Override
	public void addTargetObject(EObject targetObject) {
		for (ParameterBinding b : argumentResolutions.keySet()) {
			if (b instanceof ObjectParameterBinding) {
				ObjectArgumentWrapper arg = (ObjectArgumentWrapper) argumentResolutions.get(b);
				if (!arg.isResolved() && arg.getTargetObject() == targetObject) {
					arg.restoreResolution();
				}
			}
			// We do not need to restore multi argument resolutions because they
			// have been deleted.
		}
	}

	@Override
	public void setMinReliability(float minReliability) {
		this.minReliability = minReliability;
	}

	@Override
	public float getReliability(ObjectParameterBinding binding, EObject targetObject) {
		EObject originObject = binding.getActualA();
		if (originObject != null && matching.getCorrespondingObjectInB(originObject) == targetObject) {
			return matching.getReliability(originObject, targetObject);
		}

		return 0.0f;
	}

	@Override
	public boolean isModified(EObject targetObject) {
		if (modDetector != null) {
			return modDetector.isModified(targetObject);
		}
		return false;
	}

	private EObject resolveOriginObject(EObject originObject) {

		if (matching.getCorrespondingObjectInB(originObject) != null) {
			return matching.getCorrespondingObjectInB(originObject);
		}

		EObjectLocation location = EMFResourceUtil.locate(originModel, originObject);

		if (location == EObjectLocation.PACKAGE_REGISTRY) {
			Correspondence c = SymmetricFactory.eINSTANCE.createCorrespondence(originObject, originObject);
			c.setReliability(1.0f);
			;
			matching.addCorrespondence(c);
			return originObject;
		}
		if (location == EObjectLocation.RESOURCE_SET_INTERNAL) {
			// TODO (TK)
		}

		return null;
	}

}

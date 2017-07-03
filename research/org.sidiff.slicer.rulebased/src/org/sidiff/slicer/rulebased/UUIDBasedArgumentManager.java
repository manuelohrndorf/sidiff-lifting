package org.sidiff.slicer.rulebased;

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
import org.sidiff.common.emf.EMFUtil;
import org.sidiff.common.emf.ExternalReferenceCalculator;
import org.sidiff.common.emf.access.EMFMetaAccess;
import org.sidiff.common.emf.access.ExternalReferenceContainer;
import org.sidiff.common.emf.access.Scope;
import org.sidiff.conflicts.modifieddetector.IModifiedDetector;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.MultiParameterBinding;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.OperationInvocation;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ParameterMapping;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.patching.arguments.ArgumentWrapper;
import org.sidiff.patching.arguments.IArgumentManager;
import org.sidiff.patching.arguments.MultiArgumentWrapper;
import org.sidiff.patching.arguments.ObjectArgumentWrapper;
import org.sidiff.patching.arguments.ValueArgumentWrapper;
import org.sidiff.patching.settings.PatchMode;

public class UUIDBasedArgumentManager implements IArgumentManager {

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
	 * The changed model.
	 */
	private Resource changedModel;

	/**
	 * The target model.
	 */
	private Resource targetModel;

	/**
	 * The PackageRegistry resources that seems to be actually used.
	 */
	private Set<Resource> packageRegistryResources;

	/**
	 * The ResourceSet resources that seems to be actually used.
	 */
	private Set<Resource> resourceSetResources;

	/**
	 * The scope (resource only or complete resource set).
	 */
	private Scope scope;

	/**
	 * The patch mode (patching or updating). Updating prevents blind overwrites
	 * of target model changes.
	 */
	private PatchMode patchMode;

	/**
	 * The ModifiedDetector to which we delegate when we check if an object of
	 * the origin model has been modified in the target model.
	 */
	private IModifiedDetector modDetector;
	
	private Map<ParameterBinding, ArgumentWrapper> argumentResolutions;
	
	@Override
	public void init(AsymmetricDifference patch, Resource targetModel, Scope scope, PatchMode patchMode) {
		this.patch = patch;
		this.originModel = patch.getOriginModel();
		this.changedModel = patch.getChangedModel();
		this.targetModel = targetModel;
		this.scope = scope;
		this.patchMode = patchMode;

		// now we initialize the internal state...

		this.collectReferencedRegistryAndResourceSetResources();

		// init argument wrappers and provide initial resolutions
		argumentResolutions = new HashMap<ParameterBinding, ArgumentWrapper>();
		for (OperationInvocation invocation : patch.getOperationInvocations()) {
			for (ParameterBinding binding : invocation.getParameterBindings()) {
				if (binding instanceof ObjectParameterBinding) {
					ObjectParameterBinding objBinding = (ObjectParameterBinding) binding;
					ObjectArgumentWrapper arg = new ObjectArgumentWrapper(objBinding, this);
					EObject targetObject = null;
					String id_origin = null;
					if (objBinding.getActualA() != null) {
						id_origin = EMFUtil.getXmiId(objBinding.getActualA());
					}else if(objBinding.getActualB() != null){
						id_origin = EMFUtil.getXmiId(objBinding.getActualB());
					}
					assert id_origin != null : "no id";
					for (Iterator<EObject> iterator = targetModel.getAllContents(); iterator.hasNext();) {
						EObject eObject = iterator.next();
						String id_target = EMFUtil.getXmiId(eObject);
						if (id_origin.equals(id_target)) {
							assert id_target != null : "no id";
							targetObject = eObject;
							break;
						}
					}
					if (targetObject != null) {
						arg.resolveTo(targetObject);
						
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
						EObject targetObject = null;
						String id_origin = null;
						if (objBinding.getActualA() != null) {
							id_origin = EMFUtil.getXmiId(objBinding.getActualA());
						}else if(objBinding.getActualB() != null){
							id_origin = EMFUtil.getXmiId(objBinding.getActualB());
						}
						assert id_origin != null : "no id";
						for (Iterator<EObject> iterator = targetModel.getAllContents(); iterator.hasNext();) {
							EObject eObject = iterator.next();
							String id_target = EMFUtil.getXmiId(eObject);
							if (id_origin.equals(id_target)) {
								assert id_target != null : "no id";
								targetObject = eObject;
								break;
							}
						}
						
						if (targetObject != null) {
							nestedArg.resolveTo(targetObject);
							argumentResolutions.put(objBinding, nestedArg);
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isModified(EObject targetObject) {
		if (modDetector != null) {
			return modDetector.isModified(targetObject);
		}
		return false;
	}
	
	/**
	 * collect referenced registry and ResourceSet resources
	 */
	protected void collectReferencedRegistryAndResourceSetResources(){
		ExternalReferenceCalculator refCalculator = new ExternalReferenceCalculator();
		ExternalReferenceContainer extContainer = refCalculator.calculate(originModel, scope);
		packageRegistryResources = extContainer.getReferencedRegistryModels();
		resourceSetResources = extContainer.getReferencedResourceSetModels();
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
	
}

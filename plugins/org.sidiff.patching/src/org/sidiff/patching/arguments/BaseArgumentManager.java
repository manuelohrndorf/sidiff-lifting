package org.sidiff.patching.arguments;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.ExternalReferenceCalculator;
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
import org.sidiff.patching.PatchMode;

/**
 * An abstract argument manager that can be used as a base class to implement
 * concrete argument managers
 * 
 * @author kehrer
 */
public abstract class BaseArgumentManager implements IArgumentManager {

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
	public void init(AsymmetricDifference patch, Resource targetModel, IArgumentManagerSettings settings) {
		this.patch = patch;
		this.originModel = patch.getOriginModel();
		this.changedModel = patch.getChangedModel();
		this.targetModel = targetModel;
		this.minReliability = settings.getMinReliability();
		this.scope = settings.getScope();
		this.patchMode = settings.getPatchMode();
		this.modDetector = settings.getModifiedDetector();

		// now we initialize the internal state...

		this.collectReferencedRegistryAndResourceSetResources();

		// init argument wrappers and provide initial resolutions
		argumentResolutions = new HashMap<>();
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
					}else if(objBinding.getIncoming() == null && objBinding.getActualB() != null) {
						EObject targetObject = resolveOriginObject(objBinding.getActualB());
						if(targetObject != null) {
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
						if(objBinding.getActualA() != null) {
							EObject targetObject = resolveOriginObject(objBinding.getActualA());
							if (targetObject != null) {
								nestedArg.resolveTo(targetObject);
								argumentResolutions.put(objBinding, nestedArg);
							}
						}
					}
					argumentResolutions.put(binding, arg);
				}
			}
		}
	}

	@Override
	public ArgumentWrapper getArgument(ParameterBinding binding) {
		return argumentResolutions.get(binding);
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
	public void setMinReliability(float minReliability) {
		this.minReliability = minReliability;
	}

	@Override
	public boolean isModified(EObject targetObject) {
		if (modDetector != null) {
			return modDetector.isModified(targetObject);
		}
		return false;
	}

	/**
	 * Getter method.
	 * 
	 * @return
	 */
	protected float getMinReliability() {
		return minReliability;
	}

	/**
	 * Getter method.
	 * 
	 * @return
	 */
	protected AsymmetricDifference getPatch() {
		return patch;
	}

	/**
	 * Getter method.
	 * 
	 * @return
	 */
	protected Resource getOriginModel() {
		return originModel;
	}

	/**
	 * Getter method.
	 * 
	 * @return
	 */
	protected Resource getChangedModel() {
		return changedModel;
	}
	
	/**
	 * Getter method.
	 * 
	 * @return
	 */
	protected Resource getTargetModel() {
		return targetModel;
	}

	/**
	 * Getter method.
	 * 
	 * @return
	 */
	protected Set<Resource> getPackageRegistryResources() {
		return Collections.unmodifiableSet(packageRegistryResources);
	}

	/**
	 * Setter method.
	 * 
	 * @param packageRegistryResources
	 */
	protected void setPackageRegistryResources(Set<Resource> packageRegistryResources) {
		this.packageRegistryResources = new HashSet<>(packageRegistryResources);
	}

	/**
	 * Getter method.
	 * 
	 * @return
	 */
	protected Set<Resource> getResourceSetResources() {
		return Collections.unmodifiableSet(resourceSetResources);
	}

	/**
	 * Setter method.
	 * 
	 * @param resourceSetResources
	 */
	protected void setResourceSetResources(Set<Resource> resourceSetResources) {
		this.resourceSetResources = new HashSet<>(resourceSetResources);
	}

	/**
	 * Getter method.
	 * 
	 * @return
	 */
	protected Scope getScope() {
		return scope;
	}

	/**
	 * Getter method.
	 * 
	 * @return
	 */
	protected PatchMode getPatchMode() {
		return patchMode;
	}

	/**
	 * Getter method.
	 * 
	 * @return
	 */
	protected IModifiedDetector getModDetector() {
		return modDetector;
	}

	/**
	 * Getter method.
	 * 
	 * @return
	 */
	protected Map<ParameterBinding, ArgumentWrapper> getArgumentResolutions() {
		return argumentResolutions;
	}

	/**
	 * Template method that has to be overridden by subclasses.
	 * 
	 * @param originObject
	 * @return
	 */
	protected abstract EObject resolveOriginObject(EObject originObject);
	
	/**
	 * collect referenced registry and ResourceSet resources
	 */
	protected void collectReferencedRegistryAndResourceSetResources(){
		ExternalReferenceCalculator refCalculator = new ExternalReferenceCalculator();
		ExternalReferenceContainer extContainer = refCalculator.calculate(originModel, scope);
		setPackageRegistryResources(extContainer.getReferencedRegistryModels());
		setResourceSetResources(extContainer.getReferencedResourceSetModels());
	}
}

package org.sidiff.patching.adapter.superimposition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.sidiff.common.emf.access.EMFMetaAccess;
import org.sidiff.common.emf.access.EMFModelAccess;
import org.sidiff.common.emf.access.tree.TreeVisitor;
import org.sidiff.difference.asymmetric.AsymmetricDifference;
import org.sidiff.difference.asymmetric.MultiParameterBinding;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.ParameterBinding;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.patching.ExecutionMode;
import org.sidiff.patching.arguments.BaseArgumentManager;
import org.sidiff.patching.arguments.IArgumentManagerSettings;
import org.sidiff.patching.arguments.MultiArgumentWrapper;
import org.sidiff.patching.arguments.ObjectArgumentWrapper;
import org.sidiff.patching.arguments.ValueArgumentWrapper;
import org.sidiff.superimposition.SuperimposedElement;
import org.sidiff.superimposition.SuperimposedModel;

public class SuperimposedModelArgumentManager extends BaseArgumentManager {

	private Map<String, Set<EObject>> index;

	public SuperimposedModelArgumentManager() {
		// default constructor for extension point
	}

	@Override
	public void init(AsymmetricDifference patch, Resource targetModel, IArgumentManagerSettings settings) {
		SuperimposedModel superimposedModel = findSuperimposedModel(patch.getOriginModel());

		this.index = new HashMap<>();
		for (Iterator<EObject> iterator = targetModel.getAllContents(); iterator.hasNext();) {
			EObject eObject = iterator.next();
			String signature = superimposedModel.calculateSignature(eObject);
			if(!index.containsKey(signature)){
				index.put(signature, new HashSet<>());
			}
			index.get(signature).add(eObject);
		}
		super.init(patch, targetModel, settings);
	}

	@Override
	public void setArgument(ValueParameterBinding binding, Object value) {
		ValueArgumentWrapper argWrapper = (ValueArgumentWrapper) getArgumentResolutions().get(binding);
		argWrapper.setValue((String) value);
	}

	@Override
	public Map<Resource, Collection<EObject>> getPotentialArguments(ObjectParameterBinding binding) {
		SuperimposedElement originObject = (SuperimposedElement) binding.getActualA();
		Map<Resource, Collection<EObject>> res = new HashMap<>();

		// from target model.
		res.put(getTargetModel(), getPossibleArguments(getTargetModel(), originObject));

		// from package registry
		for (Resource r : getPackageRegistryResources()) {
			res.put(r, getPossibleArguments(r, originObject));
		}

		// from ResourceSet
		for (Resource r : getResourceSetResources()) {
			res.put(r, getPossibleArguments(r, originObject));
		}

		return res;
	}

	private static List<EObject> getPossibleArguments(Resource resource, SuperimposedElement originObject) {
		List<EObject> args = new ArrayList<EObject>();
		for(EObject obj : (Iterable<EObject>)() -> resource.getAllContents()) {
			if (EMFMetaAccess.isAssignableTo(obj.eClass(), originObject.getType())) {
				args.add(obj);
			}
		}
		return args;
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
		}
	}

	@Override
	public float getReliability(ObjectParameterBinding binding, EObject targetObject) {
		return 0;
	}

	@Override
	protected EObject resolveOriginObject(EObject originObject) {
		if(originObject instanceof SuperimposedElement){
			SuperimposedElement element = (SuperimposedElement) originObject;
			if(index.containsKey(element.getSignature())) {
				return index.get(element.getSignature()).iterator().next();
			} else if(element.isExternal()){
				return element.getObject();
			}
		}
		return null;
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
	public boolean canResolveArguments(AsymmetricDifference asymmetricDifference, Resource targetModel, IArgumentManagerSettings settings) {
		SuperimposedModel superimposedModel = findSuperimposedModel(asymmetricDifference.getOriginModel());
		if(superimposedModel == null) {
			return false;
		}
		return superimposedModel.getDocType().contains(EMFModelAccess.getCharacteristicDocumentType(targetModel));
	}

	@Override
	public ExecutionMode getExecutionMode() {
		return ExecutionMode.INTERACTIVE;
	}

	protected SuperimposedModel findSuperimposedModel(Resource resource) {
		final AtomicReference<SuperimposedModel> superimposedModel = new AtomicReference<SuperimposedModel>();
		EMFModelAccess.traverse(resource, new TreeVisitor() {
			@Override
			public boolean preExecute(EObject object) {
				if(object instanceof SuperimposedModel) {
					superimposedModel.set((SuperimposedModel)object);
					return false; // done
				}
				// also iterate over children to find superimposed model if none was found
				return superimposedModel.get() == null;
			}

			@Override
			public void postExecute(EObject object) {
				// do nothing
			}
		});
		return superimposedModel.get();
	}
}

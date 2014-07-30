package org.sidiff.patching.test;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.sidiff.difference.asymmetric.ValueParameterBinding;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.silift.common.util.emf.Scope;
import org.silift.patching.settings.PatchMode;

public class GenericCopyArgumentManager extends AbstractBatchArgumentManager {

	private Difference difference;
	private Copier copier;
	private Resource target;

	public GenericCopyArgumentManager(Difference difference) {
		this.difference = difference;
		copier = new Copier();
		copier.copyAll(difference.getSymmetric().getModelA().getContents());
		copier.copyReferences();
		
		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = difference.getSymmetric().getModelA().getURI();
		uri.appendFragment("copy");
		target = resourceSet.createResource(uri);
		target.getContents().add(copier.get(difference.getSymmetric().getModelA().getContents().get(0)));
		
		init(difference.getAsymmetric(), target, Scope.RESOURCE, PatchMode.PATCHING);
	}
	
	@Override
	public Resource getTargetModel() {
		return target;
	}

	@Override
	protected EObject resolve(EObject originObject) {
		if (originObject.eResource().equals(difference.getSymmetric().getModelA())) {
			return copier.get(originObject);
		} else {
			return originObject;
		}
	}

}

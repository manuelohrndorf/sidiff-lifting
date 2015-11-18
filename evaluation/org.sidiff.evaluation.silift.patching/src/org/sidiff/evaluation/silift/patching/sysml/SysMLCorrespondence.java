package org.sidiff.evaluation.silift.patching.sysml;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.evaluation.silift.patching.AbstractBatchArgumentManager;
import org.sidiff.patching.settings.PatchMode;
import org.silift.common.util.emf.Scope;

public class SysMLCorrespondence extends AbstractBatchArgumentManager {

	private Difference difference;
	private Copier copier;

	public SysMLCorrespondence(Difference difference) {
		this.difference = difference;
		copier = new Copier();
		copier.copyAll(difference.getSymmetric().getModelA().getContents());
		copier.copyReferences();
		
		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = difference.getSymmetric().getModelA().getURI();
		uri.appendFragment("copy");
		Resource target = resourceSet.createResource(uri);
		for (EObject origRoot : difference.getSymmetric().getModelA().getContents()) {
			target.getContents().add(copier.get(origRoot));
		}
		
		init(difference.getAsymmetric(), target, Scope.RESOURCE, PatchMode.PATCHING);
	}

	@Override
	public float getReliability(ObjectParameterBinding binding, EObject targetObject) {
		return 1.0f;
	}
	
	@Override
	public Resource getTargetModel() {
		return super.getTargetModel();
	}

	@Override
	protected EObject resolveOriginObject(EObject eObject) {
		if (eObject.eResource().equals(difference.getSymmetric().getModelA())) {
			return copier.get(eObject);
		} else {
			return eObject;
		}
	}

}

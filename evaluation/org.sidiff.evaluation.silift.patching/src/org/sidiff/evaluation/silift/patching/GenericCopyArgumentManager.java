package org.sidiff.evaluation.silift.patching;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.patching.settings.PatchMode;
import org.silift.common.util.emf.Scope;

public class GenericCopyArgumentManager extends AbstractBatchArgumentManager {

	/**
	 * The {@link Difference} representing the patch to be executed.
	 */
	private Difference difference;

	/**
	 * The copier which is used to create the target resource as a copy of the
	 * original one from whic hthe patch has been created.
	 */
	private Copier copier;

	public GenericCopyArgumentManager(Difference difference) {
		this.difference = difference;
		copier = new Copier();
		copier.copyAll(difference.getSymmetric().getModelA().getContents());
		copier.copyReferences();

		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = difference.getSymmetric().getModelA().getURI();
		uri.appendFragment("copy");
		Resource target = resourceSet.createResource(uri);
		target.getContents().add(copier.get(difference.getSymmetric().getModelA().getContents().get(0)));

		init(difference.getAsymmetric(), target, Scope.RESOURCE, PatchMode.PATCHING);
	}

	@Override
	public float getReliability(ObjectParameterBinding binding, EObject targetObject) {
		return 1.0f;
	}

	@Override
	protected EObject resolveOriginObject(EObject originObject) {
		if (originObject.eResource().equals(difference.getSymmetric().getModelA())) {
			return copier.get(originObject);
		} else {
			return originObject;
		}
	}

}

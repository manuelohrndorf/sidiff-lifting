package org.sidiff.patching.test.gmf;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.patching.test.AbstractBatchArgumentManager;
import org.silift.common.util.emf.Scope;

public class GMFCorrespondence extends AbstractBatchArgumentManager {

	private Difference difference;
	private Copier copier;
	private Resource target;

	

	public GMFCorrespondence(Difference difference) {
		this.difference = difference;
		copier = new Copier();
		copier.copyAll(difference.getSymmetric().getModelA().getContents());
		copier.copyReferences();
		
		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = difference.getSymmetric().getModelA().getURI();
		uri.appendFragment("copy");
		target = resourceSet.createResource(uri);
		target.getContents().add(copier.get(difference.getSymmetric().getModelA().getContents().get(0)));
		
		init(difference.getAsymmetric(), target, Scope.RESOURCE);
	}

	@Override
	public Resource getOriginModel() {
		return difference.getSymmetric().getModelA();
	}

	@Override
	public Resource getTargetModel() {
		return target;
	}

	@Override
	protected EObject resolve(EObject originObject) {
		if (originObject.eResource().equals(getOriginModel())) {
			return copier.get(originObject);
		} else {
			return originObject;
		}
	}

}

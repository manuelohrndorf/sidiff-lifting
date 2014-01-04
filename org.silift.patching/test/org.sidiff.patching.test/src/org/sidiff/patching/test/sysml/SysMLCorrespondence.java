package org.sidiff.patching.test.sysml;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.patching.test.AbstractBatchArgumentManager;

public class SysMLCorrespondence extends AbstractBatchArgumentManager {

	private Difference difference;
	private Copier copier;
	private Resource target;

	public SysMLCorrespondence(Difference difference) {
		this.difference = difference;
		copier = new Copier();
		copier.copyAll(difference.getSymmetric().getModelA().getContents());
		copier.copyReferences();
		
		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = difference.getSymmetric().getModelA().getURI();
		uri.appendFragment("copy");
		target = resourceSet.createResource(uri);
		for (EObject origRoot : difference.getSymmetric().getModelA().getContents()) {
			target.getContents().add(copier.get(origRoot));
		}	
	}

	@Override
	public Resource getTargetModel() {
		return target;
	}

	@Override
	protected EObject resolve(EObject eObject) {
		if (eObject.eResource().equals(difference.getSymmetric().getModelA())) {
			return copier.get(eObject);
		} else {
			return eObject;
		}
	}

}

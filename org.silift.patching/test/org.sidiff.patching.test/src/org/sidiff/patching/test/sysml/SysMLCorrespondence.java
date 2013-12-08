package org.sidiff.patching.test.sysml;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.patching.IPatchCorrespondence;

public class SysMLCorrespondence implements IPatchCorrespondence {

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
	public void set(Resource modelA, Resource modelB) {

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
	public EObject getCorrespondence(EObject eObject) {
		if (eObject.eResource().equals(getOriginModel())) {
			return copier.get(eObject);
		} else {
			return eObject;
		}
	}

	@Override
	public void setCorrespondence(EObject elementA, EObject elementB) {

	}

	@Override
	public void removeCorrespondence(EObject eObject) {

	}

	@Override
	public void addNewEObject(EObject eObject) {

	}

	@Override
	public void removeEObject(EObject eObject) {

	}

	@Override
	public Map<Resource, Collection<EObject>> getPotentialArguments(EObject eObject) {
		return null;
	}

	@Override
	public void setMinReliability(float minReliability) {

	}

	@Override
	public float getReliability(EObject objectA, EObject objectB) {
		return 0;
	}

	@Override
	public boolean isModified(EObject object) {
		return false;
	}

}

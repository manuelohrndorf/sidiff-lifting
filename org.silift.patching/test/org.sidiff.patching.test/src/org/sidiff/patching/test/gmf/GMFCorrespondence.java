package org.sidiff.patching.test.gmf;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.sidiff.difference.asymmetric.facade.util.Difference;
import org.sidiff.patching.IPatchCorrespondence;

public class GMFCorrespondence implements IPatchCorrespondence {

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
	}

	@Override
	public void set(Resource modelA, Resource modelB) {

	}

	@Override
	public Resource getModelA() {
		return difference.getSymmetric().getModelA();
	}

	@Override
	public Resource getModelB() {
		return target;
	}

	@Override
	public EObject getCorrespondence(EObject eObject) {
		if (eObject.eResource().equals(getModelA())) {
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
	public Collection<EObject> getAllCorrespondences(EObject eObject) {
		return null;
	}

	@Override
	public void setMinReliability(float minReliability) {

	}

	@Override
	public float getReliability(EObject objectA, EObject objectB) {
		return 0;
	}

}

package org.sidiff.patching.test.smg;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.patching.IPatchCorrespondence;

public class SMGPatchCorrespondence implements IPatchCorrespondence {

	private Resource modelA;
	private Resource modelB;

	public SMGPatchCorrespondence(Resource original) {
		this.modelA = original;
		this.modelB = new ResourceImpl();
		this.modelB.getContents().add(EcoreUtil.copy(original.getContents().get(0)));
		this.modelB.setURI(this.modelA.getURI().appendFileExtension("copy"));
	}

	@Override
	public void set(Resource modelA, Resource modelB) {

	}

	@Override
	public Resource getModelA() {
		return modelA;
	}

	@Override
	public Resource getModelB() {
		return modelB;
	}

	@Override
	public EObject getCorrespondence(EObject eObject) {
		if (isContainedInModelA(eObject)) {
			String fragment = EcoreUtil.getURI(eObject).fragment();
			EObject correspondence = this.modelB.getEObject(fragment);
			return correspondence;
		} else {
			return eObject;
		}
	}

	private boolean isContainedInModelA(EObject object) {
		for (Iterator<EObject> iterator = modelA.getAllContents(); iterator.hasNext();) {
			EObject o = iterator.next();
			if (object.equals(o)) {
				return true;
			}
		}

		return false;
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

	@Override
	public boolean isModified(EObject object) {
		// TODO Auto-generated method stub
		return false;
	}

}

package org.sidiff.evaluation.silift.patching.smg;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sidiff.difference.asymmetric.ObjectParameterBinding;
import org.sidiff.evaluation.silift.patching.AbstractBatchArgumentManager;

public class SMGPatchCorrespondence extends AbstractBatchArgumentManager {

	private Resource modelA;
	private Resource modelB;

	public SMGPatchCorrespondence(Resource original) {
		this.modelA = original;
		this.modelB = new ResourceImpl();
		this.modelB.getContents().add(EcoreUtil.copy(original.getContents().get(0)));
		this.modelB.setURI(this.modelA.getURI().appendFileExtension("copy"));
	}

	@Override
	public Resource getTargetModel() {
		return modelB;
	}

	@Override
	public float getReliability(ObjectParameterBinding binding, EObject targetObject) {
		return 1.0f;
	}

	@Override
	public EObject resolveOriginObject(EObject eObject) {
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

}

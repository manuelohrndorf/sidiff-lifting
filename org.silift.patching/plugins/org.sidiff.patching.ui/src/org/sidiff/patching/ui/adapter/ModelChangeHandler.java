package org.sidiff.patching.ui.adapter;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.sidiff.patching.IPatchCorrespondence;

public class ModelChangeHandler implements ModelAdapter.IModelChangeListener {

	private IPatchCorrespondence correspondence;

	public ModelChangeHandler(IPatchCorrespondence correspondence) throws FileNotFoundException, IOException {
		this.correspondence = correspondence;
	}

	@Override
	public void objectAdded(EObject eObject) {
		correspondence.addNewTargetObject(eObject);
	}

	@Override
	public void objectRemoved(EObject eObject) {
		correspondence.removeTargetObject(eObject);
	}

}

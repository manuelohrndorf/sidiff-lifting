package org.sidiff.patching.ui.adapter;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.sidiff.patching.arguments.IArgumentManager;

public class ModelChangeHandler implements IModelChangeListener {

	private IArgumentManager argumentManager;

	public ModelChangeHandler(IArgumentManager argumentManager) {
		this.argumentManager = argumentManager;
	}

	@Override
	public void objectRemoved(EObject eObject) {
		argumentManager.removeTargetObject(eObject);
	}

	@Override
	public void objectAdded(EObject eObject) {
		argumentManager.addTargetObject(eObject);
	}

	@Override
	public void referenceAdded(EReference referenceType, EObject src, EObject tgt) {
		// were not interested in this event
	}

	@Override
	public void referenceRemoved(EReference referenceType, EObject src, EObject tgt) {
		// were not interested in this event
	}

	@Override
	public void attributeValueSet(EAttribute attribute, EObject object, Object value) {
		// were not interested in this event
	}

}
